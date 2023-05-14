package com.sipc.events.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.Service.TodayInHistoryService;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.TodayInHistoryParam;
import org.springframework.stereotype.Service;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.common.eventCommon.FunParam.HISTORY_URL;
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;
@Service
public class TodayInHistoryServiceImpl implements TodayInHistoryService {
    public void todayInHistory(MessageEventParam messageEventParam){
        TodayInHistoryParam todayInHistoryParam = JSONObject.parseObject(sendHttpRequest(HISTORY_URL), TodayInHistoryParam.class);
        String str = todayInHistoryParam.getWb().replaceAll("-.*?】","\n");
        str = str.substring(1);
        StringBuilder sb =new StringBuilder();
        sb.append("历史上的今天：\n").append(str);
        sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(sb), false);
    }
}
