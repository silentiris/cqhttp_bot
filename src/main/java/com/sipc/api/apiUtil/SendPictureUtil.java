package com.sipc.api.apiUtil;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.param.sendGroupMsgParam.SendGroupMsgParam;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;
@Slf4j
public class SendPictureUtil {
    public static int sendGroupPicture(String fileName,String pictureUrl,int group_id,String message,boolean auto_escape) {
        StringBuilder sendMsgParam = new StringBuilder();
        sendMsgParam.append(message);
        sendMsgParam.append("[CQ:image,file=").append(fileName).append(",subType=0,url=").append(pictureUrl).append("]");
        StringBuilder sb = new StringBuilder();
        sb.append("group_id=").append(group_id);
        log.info(String.valueOf(sendMsgParam));
        sb.append("&message=").append(URLEncoder.encode(String.valueOf(sendMsgParam), StandardCharsets.UTF_8));
        sb.append("&auto_escape=").append(auto_escape);
        SendGroupMsgParam sendGroupMsgParam = JSONObject.parseObject(sendHttpRequest("http://127.0.0.1:8077/send_group_msg?" + sb,true), SendGroupMsgParam.class);
        try {
            return Integer.parseInt(sendGroupMsgParam.getData().getMessage_id());
        } catch (Exception e) {
            return -1;
        }
    }
    public static int sendPrivatePicture(String fileName,String pictureUrl,long user_id,String message,boolean auto_escape) {
        StringBuilder sendMsgParam = new StringBuilder();
        sendMsgParam.append(message);
        sendMsgParam.append("[CQ:image,file=").append(fileName).append(",subType=0,url=").append(pictureUrl).append("]");
        StringBuilder sb = new StringBuilder();
        sb.append("user_id=").append(user_id);
        log.info(String.valueOf(sendMsgParam));
        sb.append("&message=").append(URLEncoder.encode(String.valueOf(sendMsgParam), StandardCharsets.UTF_8));
        sb.append("&auto_escape=").append(auto_escape);
        SendGroupMsgParam sendGroupMsgParam = JSONObject.parseObject(sendHttpRequest("http://127.0.0.1:8077/send_private_msg?" + sb,true), SendGroupMsgParam.class);
        try {
            return Integer.parseInt(sendGroupMsgParam.getData().getMessage_id());
        } catch (Exception e) {
            return -1;
        }
    }
}

