package com.sipc.events.controller;

import com.sipc.events.Service.DiodeBotService;
import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.sipc.common.globalCommon.GlobalCommon.BOT_ID;

@Controller
public class MessageController {
    @Autowired
    private CustomFunController customFunController;
    @Autowired
    private DiodeBotService diodeBotService;
    public void messageHandler(MessageEventParam messageEventParam) {
        System.out.println("message received :"+messageEventParam.getMessage());
        diodeBotService.diodeBot(messageEventParam);
        if (messageEventParam.getMessage().contains("[CQ:at,qq="+BOT_ID+"] ")) customFunController.CustomFunHandler(messageEventParam);
    }
}



