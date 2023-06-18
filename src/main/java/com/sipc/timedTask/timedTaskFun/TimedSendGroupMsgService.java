package com.sipc.timedTask.timedTaskFun;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.result.GroupInfo;
import com.sipc.events.Util.GetUnmutedGroupUtil;
import com.sipc.events.entity.param.WeatherParam.Today.TodayWeatherParam;
import com.sipc.timedTask.entity.bingPicParam.BingPicParam;
import com.sipc.timedTask.entity.dailyProverbParam.DailyProverbParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static com.sipc.api.apiUtil.GetGroupListUtil.getGroupList;
import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendPictureUtil.sendGroupPicture;
import static com.sipc.common.eventCommon.FunParam.WEATHER_URL;
import static com.sipc.common.timedTaskCommon.TimedTaskFunCommon.*;
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;

@Service
public class TimedSendGroupMsgService {
    @Autowired
    private GetUnmutedGroupUtil getUnmutedGroupUtil;
    public void timedSendGroupMsg(){
        LocalDateTime now = LocalDateTime.now();
        LocalTime time = now.toLocalTime();
        if(time.getHour()==8){
            sendMorningGroupMsg();
        } else if(time.getHour()==22){
            sendNightGroupMsg();
        }else {
            sendHourlyMsg();
        }
    }
    public void sendMorningGroupMsg(){
        TodayWeatherParam todayWeatherParam = JSONObject.parseObject(sendHttpRequest(WEATHER_URL,true), TodayWeatherParam.class);
        String date = todayWeatherParam.getInfo().getDate();
        String week = todayWeatherParam.getInfo().getWeek();
        StringBuilder msg = new StringBuilder();
        date = date.replace("-"," ");
        msg.append("早上好！").append("\n")
                .append ( "今天是：").append(date).append("   ").append(week).append("\n");
        DailyProverbParam dailyProverbParam= JSONObject.parseObject(sendHttpRequest(DAILYPROVERB_URL,true), DailyProverbParam.class);
        msg.append(dailyProverbParam.getData().getZh()).append("\n");
        msg.append(dailyProverbParam.getData().getEn()).append("\n");
        msg.append("祝您度过美好的一天，心情愉悦！"+"\n");
        msg.append("来看看今天的新闻吧！").append("\n");
        String newsUrl = sendHttpRequest(QUICKNEWS_URL,false);
        String finalDate = date;
        getUnmutedGroupUtil.getUnmutedGroup().stream().forEach(
                p-> sendGroupPicture(finalDate +"bingPic",newsUrl,p, String.valueOf(msg),false));
    }
    public void sendNightGroupMsg(){
        StringBuilder msg = new StringBuilder();
        msg.append("现在是22:00,晚安！").append("\n").append("\n")
            .append("每日一言： ").append("\n")
                .append(sendHttpRequest(SINGLEDAILYPROVERB_URL,true)).append("\n").append("\n")
                .append("祝您一晚好梦！");
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = now.toLocalDate();
        BingPicParam bingPicParam = JSONObject.parseObject(sendHttpRequest(BINGPIC_URL,true), BingPicParam.class);
        String finalDate = String.valueOf(date);
        getUnmutedGroupUtil.getUnmutedGroup().stream().forEach(
                p-> sendGroupPicture(finalDate +"bingPic", String.valueOf(bingPicParam),p, String.valueOf(msg),false));
    }
    public void sendHourlyMsg(){
        LocalDateTime now = LocalDateTime.now();
        StringBuilder msg = new StringBuilder();
        msg.append("tuTu为你报时啦！").append("\n")
                .append("现在是").append(String.valueOf(now.getHour()).trim()).append("点").append("\n")
                .append("快去学习！！！");
        getUnmutedGroupUtil.getUnmutedGroup().stream().forEach(
                p-> sendGroupMsg(p, String.valueOf(msg),false));
    }
}
