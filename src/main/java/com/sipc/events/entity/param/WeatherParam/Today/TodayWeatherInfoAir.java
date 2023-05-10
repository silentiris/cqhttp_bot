package com.sipc.events.entity.param.WeatherParam.Today;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class TodayWeatherInfoAir {
    private int aqi;
    private int aqi_level;
    private String aqi_name;
    private String co;
    private String no2;
    private String o3;
    private String pm10;
    @JSONField(name="pm2.5")
    private String pm25;
    private String so2;
}
