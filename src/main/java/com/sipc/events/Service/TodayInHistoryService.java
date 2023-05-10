package com.sipc.events.Service;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.TodayInHistoryParam;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.common.eventCommon.FunParam.HISTORY_URL;
@Service
public class TodayInHistoryService {
    public void todayInHistory(MessageEventParam messageEventParam){
        TodayInHistoryParam todayInHistoryParam = null;
        boolean isGetText = false;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(HISTORY_URL);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                isGetText = true;
                todayInHistoryParam = JSONObject.parseObject(EntityUtils.toString(responseEntity), TodayInHistoryParam.class);
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
        if(isGetText){
            assert todayInHistoryParam != null;
            String str = todayInHistoryParam.getWb().replaceAll("-.*?】","\n");
            str = str.substring(1);
            StringBuilder sb =new StringBuilder();
            sb.append("历史上的今天：\n").append(str);
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(sb), false);
        }
    }

}
