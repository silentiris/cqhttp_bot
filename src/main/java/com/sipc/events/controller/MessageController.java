package com.sipc.events.controller;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.sipc.events.Service.ChatGptService;
import com.sipc.events.Service.DiodeBotService;
import com.sipc.events.entity.param.MessageEventParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimeZone;
@Slf4j
@Controller
public class MessageController {
    @Autowired
    private CustomFunController customFunController;
    @Autowired
    private DiodeBotService diodeBotService;
    @Autowired
    private ChatGptService chatGptService;
    public void messageHandler(MessageEventParam messageEventParam) {
        log.info("received:{}",messageEventParam.getMessage());
        messageEventParam.setMessage(ZhConverterUtil.toSimple(messageEventParam.getMessage()));
        if (messageEventParam.getMessage().startsWith("/")){
            customFunController.CustomFunHandler(messageEventParam);
        }
        if(messageEventParam.getGroup_id()!=794097609){
            diodeBotService.diodeBot(messageEventParam);
        }
    }
}