package com.sipc.events.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.Service.QueryWeatherService;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.WeatherParam.Today.TodayWeatherInfo;
import com.sipc.events.entity.param.WeatherParam.Today.TodayWeatherParam;
import com.sipc.events.entity.param.WeatherParam.Week.WeekWeatherData;
import com.sipc.events.entity.param.WeatherParam.Week.WeekWeatherParam;
import org.springframework.stereotype.Service;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendMsgUtil.sendPrivateMsg;
import static com.sipc.common.eventCommon.FunParam.WEATHER_FALSE;
import static com.sipc.common.eventCommon.FunParam.WEATHER_URL;
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;
@Service
public class QueryWeatherServiceImpl implements QueryWeatherService {
    public void queryWeather(MessageEventParam messageEventParam) {
        boolean isWeek = false;
        String message = messageEventParam.getMessage().substring(messageEventParam.getMessage().indexOf("天气") + 2).trim();
        message = message.trim();
        String city = null;
        String week = null;
        if (message.contains("，")) {
            isWeek = true;
            String[] paramList = message.split("，");
            if (paramList.length > 2) {
                if(messageEventParam.getGroup_id()!=0){
                    sendGroupMsg(messageEventParam.getGroup_id(), "参数有误！",false );
                }else {
                    sendPrivateMsg(messageEventParam.getUser_id(), "参数有误！",false);
                }
            } else {
                city = paramList[0];
                week = paramList[1];
                if(null==week||week.equals(" ")){
                    if(messageEventParam.getGroup_id()!=0){
                        sendGroupMsg(messageEventParam.getGroup_id(), "日期参数不能为空！",false );
                    }else {
                        sendPrivateMsg(messageEventParam.getUser_id(), "日期参数不能为空！",false);
                    }
                }
            }
        } else {
            city = message;
        }
        System.out.println(city);
        if (!isWeek) {
            boolean isGetWeather;
            StringBuilder param = new StringBuilder("?city=" + city);
            isGetWeather = true;
            TodayWeatherParam todayWeatherParam = JSONObject.parseObject(sendHttpRequest(WEATHER_URL + param,true), TodayWeatherParam.class);
            if (todayWeatherParam.getSuccess().equals(WEATHER_FALSE)) {
                if(messageEventParam.getGroup_id()!=0){
                    sendGroupMsg(messageEventParam.getGroup_id(), "查询失败,仅支持国内主要省,精确到城市",false );
                }else {
                    sendPrivateMsg(messageEventParam.getUser_id(), "查询失败,仅支持国内主要省,精确到城市",false);
                }
                isGetWeather = false;
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
                String s = String.valueOf(returnparam).replace("-"," ");
                if(messageEventParam.getGroup_id()!=0){
                    sendGroupMsg(messageEventParam.getGroup_id(), s,false );
                }else {
                    sendPrivateMsg(messageEventParam.getUser_id(), s,false);
                }
            }
        } else {
            StringBuilder param = new StringBuilder("?city=" + city + "&type=week");
            WeekWeatherParam weekWeatherParam = JSONObject.parseObject(sendHttpRequest(WEATHER_URL+param,true), WeekWeatherParam.class);
            boolean isGetWeather = true;
            if (weekWeatherParam.getSuccess().equals(WEATHER_FALSE)) {
                if(messageEventParam.getGroup_id()!=0){
                    sendGroupMsg(messageEventParam.getGroup_id(), "查询失败,仅支持国内主要省,精确到城市",false );
                }else {
                    sendPrivateMsg(messageEventParam.getUser_id(), "查询失败,仅支持国内主要省,精确到城市",false);
                }
                isGetWeather = false;
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
                        if(messageEventParam.getGroup_id()!=0){
                            sendGroupMsg(messageEventParam.getGroup_id(), "星期的天数输入有误。请按照标准格式输入。",false );
                        }else {
                            sendPrivateMsg(messageEventParam.getUser_id(), "星期的天数输入有误。请按照标准格式输入。",false);
                        }
                    } else {
                        StringBuilder returnparam = new StringBuilder();
                        returnparam.append(weekWeatherData.getDate()).append(" ")
                                .append(weekWeatherData.getWeek()).append(" ")
                                .append(weekWeatherData.getType()).append("\n");
                        returnparam.append("最低:").append(weekWeatherData.getLow()).append("，")
                                .append("最高:").append(weekWeatherData.getHigh()).append("，")
                                .append("风力：").append(weekWeatherData.getFengli());
                        String s = String.valueOf(returnparam).replace("-"," ");
                        if(messageEventParam.getGroup_id()!=0){
                            sendGroupMsg(messageEventParam.getGroup_id(), s,false );
                        }else {
                            sendPrivateMsg(messageEventParam.getUser_id(), s,false);
                        }
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
                    String s = String.valueOf(returnParam).replace("-"," ");
                    if(messageEventParam.getGroup_id()!=0){
                        sendGroupMsg(messageEventParam.getGroup_id(), s,false );
                    }else {
                        sendPrivateMsg(messageEventParam.getUser_id(), s,false);
                    }
                }
            }
        }
    }
}
