package com.sipc.events.Util;

import com.sipc.events.entity.param.MessageEventParam;

import java.util.Map;

public class ParamConvertUtil {
    public static MessageEventParam MessageEventParaConvert(Map paramMap){
        MessageEventParam messageEventParam = new MessageEventParam();
//        messageEventParam.setMessage_type((String) paramMap.get("message_type"));
//        messageEventParam.setSub_type((String) paramMap.get("sub_type"));
//        messageEventParam.setRaw_message((String) paramMap.get("raw_message"));
//        messageEventParam.setMessage_id((Integer) paramMap.get("message_id"));
//        messageEventParam.setUser_id((Integer) paramMap.get("user_id"));
        messageEventParam.setMessage((String) paramMap.get("message"));
        messageEventParam.setGroup_id((Integer) paramMap.get("group_id"));
        return messageEventParam;
    }
}
