package com.sipc.api.apiUtil;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.param.sendGroupMsgParam.SendGroupMsgParam;
import com.sipc.api.entity.param.sendGroupMsgParam.SendGroupMsgParamData;
import com.sipc.events.entity.param.DailyNewsParam.DailyNewsParam;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;

@Service
public class SendMsgUtil {
    public static int sendGroupMsg(int group_id,String message,boolean auto_escape) {
        Date currentTime = new Date();
        String date = currentTime.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("group_id=").append(group_id);
        sb.append("&message=").append(URLEncoder.encode(message, StandardCharsets.UTF_8));
        sb.append("&auto_escape=").append(auto_escape);
        SendGroupMsgParam sendGroupMsgParam = JSONObject.parseObject(sendHttpRequest("http://127.0.0.1:8077/send_group_msg?" + sb,true), SendGroupMsgParam.class);
        try{
            System.out.println( date+" "+"send group message :"+message);
            return Integer.parseInt(sendGroupMsgParam.getData().getMessage_id());
        }catch (Exception e){
            return -1;
        }
    }
    public static int sendPrivateMsg(long user_id,String message,boolean auto_escape){
        Date currentTime = new Date();
        String date = currentTime.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("user_id=").append(user_id);
        sb.append("&message=").append(URLEncoder.encode(message, StandardCharsets.UTF_8));
        sb.append("&auto_escape=").append(auto_escape);
        SendGroupMsgParam sendGroupMsgParam = JSONObject.parseObject(sendHttpRequest("http://127.0.0.1:8077/send_private_msg?" + sb,true), SendGroupMsgParam.class);
        try{
            System.out.println( date+" "+"send private message :"+message);
            return Integer.parseInt(sendGroupMsgParam.getData().getMessage_id());
        }catch (Exception e){
            return -1;
        }
    }

}
