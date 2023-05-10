package com.sipc.timedTask.timedTaskFun;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.result.GroupInfo;
import com.sipc.events.Service.QueryWeatherService;
import com.sipc.events.entity.param.WeatherParam.Today.TodayWeatherParam;
import com.sipc.timedTask.entity.dailyProverbParam.DailyProverbParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import static com.sipc.api.apiUtil.GetGroupListUtil.getGroupList;
import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendPictureUtil.sendPicture;
import static com.sipc.common.eventCommon.FunParam.WEATHER_FALSE;
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
        }
        if(time.getHour()==22){
            sendNightGroupMsg();
        }
    }
    public void sendMorningGroupMsg(){
        String date = null;
        String week = null;

        try {
            URL url = new URL(WEATHER_URL );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/json;charset=utf-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String response = responseBuilder.toString();
            TodayWeatherParam todayWeatherParam = JSONObject.parseObject(response, TodayWeatherParam.class);
            date = todayWeatherParam.getInfo().getDate();
            week = todayWeatherParam.getInfo().getWeek();
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder msg = new StringBuilder();
        date = date.replace("-"," ");
        msg.append("早上好！").append("\n")
                .append ( "今天是：").append(date).append("   ").append(week).append("\n");

        try {
            URL url = new URL(DAILYPROVERB_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/json;charset=utf-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String response = responseBuilder.toString();
            DailyProverbParam dailyProverbParam= JSONObject.parseObject(response, DailyProverbParam.class);
            msg.append(dailyProverbParam.getData().getZh()).append("\n");
            msg.append(dailyProverbParam.getData().getEn()).append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        msg.append("祝您度过美好的一天，心情愉悦！"+"\n");
        for(GroupInfo groupInfo: Objects.requireNonNull(getGroupList())){
            sendPicture(date+" bingPic",BINGPIC_URL, groupInfo.getGroup_id(), String.valueOf(msg),false);
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
            sendPicture(date.toString()+" 60sNews",QUICKNEWS_URL, groupInfo.getGroup_id(), String.valueOf(msg),false);

        }
    }
}
