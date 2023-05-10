package com.sipc.events.entity.param.WeatherParam.Week;

import lombok.Data;

@Data
public class WeekWeatherData {
        private String date;
        private String week;
        private String type;
        private String low;
        private String high;
        private String fengxiang;
        private String fengli;
        private String night;
}
