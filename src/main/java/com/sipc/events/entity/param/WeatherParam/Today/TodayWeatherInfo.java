package com.sipc.events.entity.param.WeatherParam.Today;

import lombok.Data;

@Data
public class TodayWeatherInfo {
    private String date;
    private String week;
    private String type;
    private String low;
    private String high;
    private String fengxiang;
    private String fengli;
    private TodayWeatherInfoNight night;
    private TodayWeatherInfoAir air;
    private String tip;

}
