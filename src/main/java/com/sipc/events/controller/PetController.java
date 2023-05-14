package com.sipc.events.controller;

import com.sipc.events.Service.PetService;
import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.sipc.common.globalCommon.GlobalCommon.BOT_ID;
import static com.sipc.common.globalCommon.GlobalCommon.BOT_NAME;

@Controller
public class PetController {
    @Autowired
    private PetService petService;
    public void petHandler(MessageEventParam messageEventParam){
        String message = messageEventParam.getMessage();
        if(message.contains("@"+BOT_NAME)){
            message = message.replace("@"+BOT_NAME+"兔兔","");
        }else {
            message = message.replace("[CQ:at,qq="+BOT_ID+"]兔兔","");
        }
        if(message.startsWith("领养")){
            petService.adoptPet(messageEventParam);
        }else if (message.startsWith("改名")){
            petService.changeName(messageEventParam);
        }else if (message.startsWith("couple")){
            petService.confess(messageEventParam);
        } else if (message.startsWith("同意")) {
            petService.promise(messageEventParam);
        } else if (message.startsWith("贴贴")) {
            petService.hug(messageEventParam);
        } else if (message.startsWith("早安")) {
            petService.punch(messageEventParam);
        } else if (message.startsWith("分手")) {
            petService.breakUp(messageEventParam);
        }else if(message.startsWith("比划比划")){
            petService.fight(messageEventParam);
        }else if (message.startsWith("群兔兔排行")){
            petService.selectGroupRank(messageEventParam);
        }else if (message.startsWith("我的tutu")){
            petService.selectMyPet(messageEventParam);
        }
    }
}
