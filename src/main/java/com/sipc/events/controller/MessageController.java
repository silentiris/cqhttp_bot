package com.sipc.events.controller;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.sipc.events.Service.ChatGptService;
import com.sipc.events.Service.DiodeBotService;
import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.sipc.common.globalCommon.GlobalCommon.BOT_ID;
import static com.sipc.common.globalCommon.GlobalCommon.BOT_NAME;

@Controller
public class MessageController {
    @Autowired
    private CustomFunController customFunController;
    @Autowired
    private DiodeBotService diodeBotService;
    public void messageHandler(MessageEventParam messageEventParam) {
        messageEventParam.setMessage(messageEventParam.getMessage().replaceAll("\\s",""));
        System.out.println("message received :\n"+messageEventParam.getMessage());
        messageEventParam.setMessage(ZhConverterUtil.toSimple(messageEventParam.getMessage()));
        if (messageEventParam.getMessage().startsWith("/")){
            customFunController.CustomFunHandler(messageEventParam);
        }
        diodeBotService.diodeBot(messageEventParam);
    }
}