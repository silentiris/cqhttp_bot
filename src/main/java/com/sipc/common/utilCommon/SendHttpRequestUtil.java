package com.sipc.common.utilCommon;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
public class SendHttpRequestUtil {
    public static String sendHttpRequest(String urlParam, boolean autoJump) {
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
            if (autoJump) {
                return response;
            } else {
                return headers.get("Location").get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("http request err!");
            return null;
        }
    }

    public static String sendHttpRequest(String urlParam, String name, String value, boolean autoJump) {
        try {
            URL url = new URL(urlParam);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(autoJump);
            connection.setRequestMethod("GET");
            connection.setRequestProperty(name, value);
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
            if (autoJump) {
                return response;
            } else {
                return headers.get("Location").get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(" http(with cookie) request err!");
            return null;
        }
    }
    public static String sendPostHttpRequest(String urlParam, String jsonParam) {
        try {
            URL url = new URL(urlParam);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setDoOutput(true); // 允许写入请求体
            // 设置请求体数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonParam.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
            // 读取响应内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
