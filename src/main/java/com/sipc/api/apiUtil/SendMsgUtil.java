package com.sipc.api.apiUtil;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.param.sendGroupMsgParam.SendGroupMsgParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;
@Slf4j
@Service
public class SendMsgUtil {
    public static int sendGroupMsg(int group_id,String message,boolean auto_escape) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //拼接请求参数
        StringBuilder sb = new StringBuilder();
        sb.append("group_id=").append(group_id);
        sb.append("&message=").append(URLEncoder.encode(message, StandardCharsets.UTF_8));
        sb.append("&auto_escape=").append(auto_escape);
        //发送http请求，并且将response解析为Param对象
        SendGroupMsgParam sendGroupMsgParam = JSONObject.parseObject(sendHttpRequest("http://127.0.0.1:8077/send_group_msg?" + sb,true), SendGroupMsgParam.class);
        //打印日志：
        try{
            log.info("send: {}",message);
            return Integer.parseInt(sendGroupMsgParam.getData().getMessage_id());
        }catch (Exception e){
            return -1;
        }
    }
    public static int sendPrivateMsg(long user_id,String message,boolean auto_escape){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("user_id=").append(user_id);
        sb.append("&message=").append(URLEncoder.encode(message, StandardCharsets.UTF_8));
        sb.append("&auto_escape=").append(auto_escape);
        SendGroupMsgParam sendGroupMsgParam = JSONObject.parseObject(sendHttpRequest("http://127.0.0.1:8077/send_private_msg?" + sb,true), SendGroupMsgParam.class);
        try{
            log.info("send:{}",message);
            return Integer.parseInt(sendGroupMsgParam.getData().getMessage_id());
        }catch (Exception e){
            return -1;
        }
    }

}
