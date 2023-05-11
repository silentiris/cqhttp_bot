package com.sipc.common.utilCommon;

import com.alibaba.fastjson.JSONObject;
import com.sipc.timedTask.entity.dailyProverbParam.DailyProverbParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.sipc.common.timedTaskCommon.TimedTaskFunCommon.DAILYPROVERB_URL;

public class SendHttpRequestUtil {
    public static String sendHttpRequest(String urlParam){
        try {
            URL url = new URL(urlParam);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/json;charset=utf-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            return responseBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
