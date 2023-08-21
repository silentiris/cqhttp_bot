package com.sipc.api.apiUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sipc.api.entity.param.forwardMsg.ForwardNode;
import com.sipc.api.entity.param.forwardMsg.ForwardNodeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendPostHttpRequest;

public class SendForwardMsgUtil {
    public static boolean sendForwardMsg(int group_id, List<ForwardNode> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        Map<String,Object> map  =new HashMap<>();
        map.put("group_id",group_id);
        map.put("messages",list);
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return false;
        }
        sendPostHttpRequest("http://127.0.0.1:8077/send_group_forward_msg",json);
        return true;
    }
    public static boolean sendForwardMsgByDefault(int group_id, List<String> messageList) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ForwardNode> list = new ArrayList<>();
        for(String msg: messageList){
            ForwardNode forwardNode = new ForwardNode();
            forwardNode.setType("node");
            ForwardNodeData forwardNodeData = new ForwardNodeData();
            forwardNodeData.setUin("2291910418");
            forwardNodeData.setContent(msg);
            forwardNodeData.setName("bot");
            forwardNode.setData(forwardNodeData);
            list.add(forwardNode);
        }
        String json;
        Map<String,Object> map  =new HashMap<>();
        map.put("group_id",group_id);
        map.put("messages",list);
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return false;
        }
        sendPostHttpRequest("http://127.0.0.1:8077/send_group_forward_msg",json);
        return true;
    }
}
