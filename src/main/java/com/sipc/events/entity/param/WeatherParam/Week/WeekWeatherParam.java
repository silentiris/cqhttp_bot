package com.sipc.events.entity.param.WeatherParam.Week;

import lombok.Data;

import java.util.List;

@Data
public class WeekWeatherParam {
    private String success;
    private String city;
    private List<WeekWeatherData> data;

}
