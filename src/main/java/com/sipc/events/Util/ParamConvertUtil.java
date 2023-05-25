package com.sipc.events.Util;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.Sender;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParamConvertUtil {
    public static MessageEventParam MessageEventParaConvert(Map paramMap){
        MessageEventParam messageEventParam = new MessageEventParam();
//        messageEventParam.setMessage_type((String) paramMap.get("message_type"));
//        messageEventParam.setSub_type((String) paramMap.get("sub_type"));
//        messageEventParam.setRaw_message((String) paramMap.get("raw_message"));
//        messageEventParam.setMessage_id((Integer) paramMap.get("message_id"));
        try {
            messageEventParam.setUser_id((Long) paramMap.get("user_id"));
        }catch (Exception e){
            Integer integerId = ((Integer) paramMap.get("user_id"));
            messageEventParam.setUser_id(integerId.longValue());
        }
        messageEventParam.setMessage((String) paramMap.get("message"));
        try{
            messageEventParam.setGroup_id((Integer) paramMap.get("group_id"));
        }catch (Exception ignored){}
        messageEventParam.setSender(new Sender((String)((Map)(paramMap.get("sender"))).get("nickname")));
        return messageEventParam;
    }
}
