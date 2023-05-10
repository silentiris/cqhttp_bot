package com.sipc.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.sipc.events.Util.ParamConvertUtil.MessageEventParaConvert;

@CrossOrigin
@RestController
public class GlobalController {
    @Autowired
    private MessageController messageController;
    @PostMapping ("/")
    public void globalController(@RequestBody Map<String,Object> paramsMap){
        switch (paramsMap.get("post_type").toString()){
            case "message":
                messageController.messageHandler(MessageEventParaConvert(paramsMap));
                break;
        }
    }
}
