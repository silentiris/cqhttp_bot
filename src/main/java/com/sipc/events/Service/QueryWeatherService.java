package com.sipc.events.Service;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.WeatherParam.Today.TodayWeatherInfo;
import com.sipc.events.entity.param.WeatherParam.Today.TodayWeatherParam;
import com.sipc.events.entity.param.WeatherParam.Week.WeekWeatherData;
import com.sipc.events.entity.param.WeatherParam.Week.WeekWeatherParam;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.common.eventCommon.FunParam.*;
@Service
public class QueryWeatherService {
    public void queryWeather(MessageEventParam messageEventParam) {
        boolean isWeek = false;
        String message = messageEventParam.getMessage().substring(messageEventParam.getMessage().indexOf("天气") + 2);
        String city = null;
        String week = null;
        if (message.contains("，")) {
            isWeek = true;
            String[] paramList = message.split("，");
            if (paramList.length > 2) {
                sendGroupMsg(messageEventParam.getGroup_id(), "参数有误！", false);
            } else {
                city = paramList[0];
                week = paramList[1];
                if(null==week||week.equals(" ")){
                    sendGroupMsg(messageEventParam.getGroup_id(), "日期参数不能为空！", false);
                }
            }
        } else {
            city = message;
        }
        System.out.println(city);
        if (!isWeek) {
            boolean isGetWeather = false;
            TodayWeatherParam todayWeatherParam = new TodayWeatherParam();
            StringBuilder param = new StringBuilder("?city=" + city);
            try {
                URL url = new URL(WEATHER_URL + param);
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
                isGetWeather = true;
                todayWeatherParam = JSONObject.parseObject(response, TodayWeatherParam.class);
                if (todayWeatherParam.getSuccess().equals(WEATHER_FALSE)) {
                    sendGroupMsg(messageEventParam.getGroup_id(), "查询失败,仅支持国内主要省,精确到城市", false);
                    isGetWeather = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isGetWeather) {
                StringBuilder returnparam = new StringBuilder();
                TodayWeatherInfo todayWeatherInfo = todayWeatherParam.getInfo();
                returnparam.append(todayWeatherInfo.getDate()).append("  ")
                        .append(todayWeatherInfo.getWeek()).append("  ")
                        .append(todayWeatherInfo.getType()).append("\n");
                returnparam.append("最低:").append(todayWeatherInfo.getLow()).append("，")
                        .append("最高:").append(todayWeatherInfo.getHigh()).append("，")
                        .append("风力：").append(todayWeatherInfo.getFengli()).append("，")
                        .append("PM2.5: ").append(todayWeatherInfo.getAir().getPm25()).append("\n");
                returnparam.append("tip:").append(todayWeatherInfo.getTip());
                sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(returnparam), false);
            }
        } else {
            WeekWeatherParam weekWeatherParam = new WeekWeatherParam();
            boolean isGetWeather = false;
            StringBuilder param = new StringBuilder("?city=" + city + "&type=week");
            try {
                URL url = new URL(WEATHER_URL+param);
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
                    isGetWeather = true;
                    weekWeatherParam = JSONObject.parseObject(response, WeekWeatherParam.class);
                    if (weekWeatherParam.getSuccess().equals(WEATHER_FALSE)) {
                        sendGroupMsg(messageEventParam.getGroup_id(), "查询失败,仅支持国内主要省,精确到城市", false);
                        isGetWeather = false;
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isGetWeather) {
                assert week != null;
                if( !week.equals("未来七天")){
                    WeekWeatherData weekWeatherData = null;
                    for (WeekWeatherData wwd : weekWeatherParam.getData()) {
                        if (wwd.getWeek().equals(week)) {
                            weekWeatherData = wwd;
                        }
                    }
                    if (null == weekWeatherData) {
                        sendGroupMsg(messageEventParam.getGroup_id(), "星期的天数输入有误。请按照标准格式输入。", false);
                    } else {
                        StringBuilder returnparam = new StringBuilder();
                        returnparam.append(weekWeatherData.getDate()).append(" ")
                                .append(weekWeatherData.getWeek()).append(" ")
                                .append(weekWeatherData.getType()).append("\n");
                        returnparam.append("最低:").append(weekWeatherData.getLow()).append("，")
                                .append("最高:").append(weekWeatherData.getHigh()).append("，")
                                .append("风力：").append(weekWeatherData.getFengli());
                        sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(returnparam), false);
                    }
                }else {
                    StringBuilder returnParam = new StringBuilder();
                    returnParam.append(weekWeatherParam.getCity()).append(":").append("\n");
                    for(WeekWeatherData weekWeatherData: weekWeatherParam.getData()){
                        returnParam.append(weekWeatherData.getDate()).append(" ")
                                .append(weekWeatherData.getWeek()).append(" ")
                                .append(weekWeatherData.getType()).append("\n");
                        returnParam.append("最低:").append(weekWeatherData.getLow()).append("，")
                                .append("最高:").append(weekWeatherData.getHigh()).append("，")
                                .append("风力：").append(weekWeatherData.getFengli()).append("\n");
                    }
                    sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(returnParam), false);
                }
            }
        }
    }
}
