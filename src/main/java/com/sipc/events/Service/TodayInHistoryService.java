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
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;

@Service
public class TodayInHistoryService {
    public void todayInHistory(MessageEventParam messageEventParam){
        TodayInHistoryParam todayInHistoryParam = JSONObject.parseObject(sendHttpRequest(HISTORY_URL), TodayInHistoryParam.class);
        String str = todayInHistoryParam.getWb().replaceAll("-.*?】","\n");
        str = str.substring(1);
        StringBuilder sb =new StringBuilder();
        sb.append("历史上的今天：\n").append(str);
        sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(sb), false);
    }
}
