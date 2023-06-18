package com.sipc.common.utilCommon;

import com.alibaba.fastjson.JSONObject;
import com.sipc.timedTask.entity.dailyProverbParam.DailyProverbParam;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static com.sipc.common.timedTaskCommon.TimedTaskFunCommon.DAILYPROVERB_URL;
@Slf4j
public class SendHttpRequestUtil {
    public static String sendHttpRequest(String urlParam,boolean autoJump){
        try {
            URL url = new URL(urlParam);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(autoJump);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/json;charset=utf-8");
            // 获取响应头
            Map<String, List<String>> headers = connection.getHeaderFields();
            // 读取响应内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
            String response = responseBuilder.toString();
            log.info(response);
            if(autoJump){
                return response;
            }else {
                return headers.get("Location").get(0);
            }
        } catch (IOException e) {
            log.error("http request err!");
            return null;
        }
    }
}
