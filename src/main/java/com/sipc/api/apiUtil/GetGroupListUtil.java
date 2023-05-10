package com.sipc.api.apiUtil;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.result.GroupInfo;
import com.sipc.api.entity.result.getGroupListParam.GetGroupListResult;
import com.sipc.events.entity.param.WeatherParam.Today.TodayWeatherParam;
import jakarta.servlet.http.PushBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class GetGroupListUtil {
    public static List<GroupInfo> getGroupList(){
        try {
            URL url = new URL("http://127.0.0.1:8077/get_group_list");
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
            return JSONObject.parseObject(response, GetGroupListResult.class).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
