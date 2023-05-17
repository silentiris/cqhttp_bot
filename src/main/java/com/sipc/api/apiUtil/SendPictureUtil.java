package com.sipc.api.apiUtil;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.param.sendGroupMsgParam.SendGroupMsgParam;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;

public class SendPictureUtil {
    public static int sendPicture(String fileName,String pictureUrl,int group_id,String message,boolean auto_escape) {
        StringBuilder sendMsgParam = new StringBuilder();
        sendMsgParam.append(message);
        sendMsgParam.append("[CQ:image,file=").append(fileName).append(",subType=0,url=").append(pictureUrl).append("]");
        StringBuilder sb = new StringBuilder();
        sb.append("group_id=").append(group_id);
        System.out.println(sendMsgParam);
        sb.append("&message=").append(URLEncoder.encode(String.valueOf(sendMsgParam), StandardCharsets.UTF_8));
        sb.append("&auto_escape=").append(auto_escape);
        SendGroupMsgParam sendGroupMsgParam = JSONObject.parseObject(sendHttpRequest("http://127.0.0.1:8077/send_group_msg?" + sb,true), SendGroupMsgParam.class);
        System.out.println("send message :" + sendMsgParam + "\n");
        try {
            int message_id = Integer.parseInt(sendGroupMsgParam.getData().getMessage_id());
            System.out.println("send message :" + sendMsgParam + "\n" + "message_id:" + message_id);
            return message_id;
        } catch (Exception e) {
            return -1;
        }
    }
}

