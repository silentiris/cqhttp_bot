package com.sipc.events.entity.param.WeatherParam.Today;

import lombok.Data;

import java.lang.ref.SoftReference;
@Data
public class TodayWeatherParam {
    private String success;
    private String city;
    private TodayWeatherInfo info;
}
