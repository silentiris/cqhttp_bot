package com.sipc.events.Service.impl;

import com.github.houbb.pinyin.constant.enums.PinyinStyleEnum;
import com.github.houbb.pinyin.util.PinyinHelper;
import com.sipc.events.Service.DiodeBotService;
import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.stereotype.Service;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendMsgUtil.sendPrivateMsg;
import static com.sipc.common.eventCommon.DiodeCommon.diodeBotList;
@Service
public class DiodeBotServiceImpl implements DiodeBotService {
    public void diodeBot(MessageEventParam messageEventParam) {
        for (String str : diodeBotList) {
            if (PinyinHelper.toPinyin(messageEventParam.getMessage(), PinyinStyleEnum.NORMAL).contains(PinyinHelper.toPinyin(str, PinyinStyleEnum.NORMAL))) {
                if (PinyinHelper.toPinyin(str, PinyinStyleEnum.NORMAL).equals("bao zi")) {
                    if(messageEventParam.getGroup_id()!=0){
                        sendGroupMsg(messageEventParam.getGroup_id(), "不要使坏哦",false );
                    }else {
                        sendPrivateMsg(messageEventParam.getUser_id(), "不要使坏哦",false);
                    }
                    continue;
                }
                double temp = Math.random();
                if (temp >= 0.66) {
                    if(messageEventParam.getGroup_id()!=0){
                        sendGroupMsg(messageEventParam.getGroup_id(), str + "真不行",false );
                    }else {
                        sendPrivateMsg(messageEventParam.getUser_id(), str + "真不行",false);
                    }
                }
                else if (temp >= 0.33) {
                    if(messageEventParam.getGroup_id()!=0){
                        sendGroupMsg(messageEventParam.getGroup_id(), str + "神中神！！！",false );
                    }else {
                        sendPrivateMsg(messageEventParam.getUser_id(), str + "神中神！！！",false);
                    }
                } else{
                    if(messageEventParam.getGroup_id()!=0){
                        sendGroupMsg(messageEventParam.getGroup_id(), str + "其实感觉不如" + diodeBotList.get((int) (diodeBotList.size() * temp)) + "...",false );
                    }else {
                        sendPrivateMsg(messageEventParam.getUser_id(), str + "其实感觉不如" + diodeBotList.get((int) (diodeBotList.size() * temp)) + "...",false);
                    }
                }
            }
        }
    }
}
