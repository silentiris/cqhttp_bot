package com.sipc.api.apiUtil;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
@Service
public class SendMsgUtil {
    public static void sendGroupMsg(int group_id,String message,boolean auto_escape) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        StringBuilder sb = new StringBuilder();
        sb.append("group_id=").append(group_id);
        sb.append("&message=").append(URLEncoder.encode(message, StandardCharsets.UTF_8));
        sb.append("&auto_escape=").append(auto_escape);
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8077/send_group_msg?"+sb);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            System.out.println( "send message :"+message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
