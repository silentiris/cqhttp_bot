package com.sipc.events.Service.impl;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.util.Proxys;
import com.sipc.events.Service.ChatGptService;
import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.stereotype.Service;

import java.net.Proxy;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;

@Service
public class ChatGptServiceImpl implements ChatGptService {
    @Override
    public void chatGptMsg(MessageEventParam messageEventParam) {
        System.out.println(messageEventParam);
        String message = messageEventParam.getMessage().replace("/tk","");
        Proxy proxy = Proxys.http("127.0.0.1", 7890);

        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey("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")
                .proxy(proxy)
                .apiHost("https://api.openai.com/") //反向代理地址
                .build()
                .init();
        String msg = chatGPT.chat(message );
        sendGroupMsg(messageEventParam.getGroup_id(),msg,false );
    }
}
