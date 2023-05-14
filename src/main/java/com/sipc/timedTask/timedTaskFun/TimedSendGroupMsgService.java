package com.sipc.timedTask.timedTaskFun;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.result.GroupInfo;
import com.sipc.events.entity.param.WeatherParam.Today.TodayWeatherParam;
import com.sipc.timedTask.entity.dailyProverbParam.DailyProverbParam;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static com.sipc.api.apiUtil.GetGroupListUtil.getGroupList;
import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendPictureUtil.sendPicture;
import static com.sipc.common.eventCommon.FunParam.WEATHER_URL;
import static com.sipc.common.timedTaskCommon.TimedTaskFunCommon.*;
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;

@Service
public class TimedSendGroupMsgService {
    public void timedSendGroupMsg(){
        LocalDateTime now = LocalDateTime.now();
        LocalTime time = now.toLocalTime();
        if(time.getHour()==8){
            sendMorningGroupMsg();
        } else if(time.getHour()==22){
            sendNightGroupMsg();
        }else {
            sendHourlyMsg();
            System.out.println(now);
        }
    }
    public void sendMorningGroupMsg(){
        TodayWeatherParam todayWeatherParam = JSONObject.parseObject(sendHttpRequest(WEATHER_URL), TodayWeatherParam.class);
        String date = todayWeatherParam.getInfo().getDate();
        String week = todayWeatherParam.getInfo().getWeek();
        StringBuilder msg = new StringBuilder();
        date = date.replace("-"," ");
        msg.append("早上好！").append("\n")
                .append ( "今天是：").append(date).append("   ").append(week).append("\n");
        DailyProverbParam dailyProverbParam= JSONObject.parseObject(sendHttpRequest(DAILYPROVERB_URL), DailyProverbParam.class);
        msg.append(dailyProverbParam.getData().getZh()).append("\n");
        msg.append(dailyProverbParam.getData().getEn()).append("\n");
        msg.append("祝您度过美好的一天，心情愉悦！"+"\n");
        for(GroupInfo groupInfo: Objects.requireNonNull(getGroupList())){
            sendPicture(date+" bingPic",BINGPIC_URL,false, groupInfo.getGroup_id(), String.valueOf(msg),false);
        }
    }
    public void sendNightGroupMsg(){
        StringBuilder msg = new StringBuilder();
        msg.append("现在是22:00,晚安！").append("\n").append("\n")
            .append("每日一言： ").append("\n")
                .append(sendHttpRequest(SINGLEDAILYPROVERB_URL)).append("\n").append("\n")
                .append("来看看今天的新闻吧！").append("\n")
                .append("祝您一晚好梦！");
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        for(GroupInfo groupInfo: Objects.requireNonNull(getGroupList())){
            sendPicture(date.toString()+" 60sNews",QUICKNEWS_URL,false, groupInfo.getGroup_id(), String.valueOf(msg),false);
        }
    }
    public void sendHourlyMsg(){
        LocalDateTime now = LocalDateTime.now();
        StringBuilder msg = new StringBuilder();
        msg.append("tuTu为你报时啦！").append("\n")
                .append("现在是").append(String.valueOf(now.getHour()).trim()).append("点").append("\n")
                .append("快去学习！！！");
        for(GroupInfo groupInfo: Objects.requireNonNull(getGroupList())){
            sendGroupMsg(groupInfo.getGroup_id(), String.valueOf(msg),false);
        }
    }
}
