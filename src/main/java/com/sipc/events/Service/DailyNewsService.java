package com.sipc.events.Service;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.entity.param.DailyNewsParam;
import com.sipc.events.entity.param.MessageEventParam;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.common.eventCommon.FunParam.NEWS_URL;
@Service
public class DailyNewsService {
    public void dailyNews(MessageEventParam messageEventParam) {
        DailyNewsParam dailyNewsParam = null;
        boolean isGetNews = false;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(NEWS_URL);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                isGetNews = true;
                dailyNewsParam = JSONObject.parseObject(EntityUtils.toString(responseEntity), DailyNewsParam.class);
            }
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
        if(isGetNews){
            assert dailyNewsParam != null;
            String str = dailyNewsParam.getWb().replaceAll("【换行】","\n");
            str = str.substring(1);
            StringBuilder sb =new StringBuilder();
            sb.append("每日新闻：\n").append(str);
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(sb), false);
        }
    }
}
