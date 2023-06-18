package com.sipc.events.Service.impl;


import com.sipc.events.Service.ChatGptService;
import com.sipc.events.entity.param.MessageEventParam;
import com.unfbx.chatgpt.OpenAiClient;
import com.unfbx.chatgpt.entity.chat.ChatCompletion;
import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
import com.unfbx.chatgpt.entity.chat.Message;
import com.unfbx.chatgpt.entity.images.ImageResponse;
import com.unfbx.chatgpt.function.KeyRandomStrategy;
import com.unfbx.chatgpt.interceptor.OpenAiResponseInterceptor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.processing.Completion;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendMsgUtil.sendPrivateMsg;
import static com.sipc.api.apiUtil.SendPictureUtil.sendGroupPicture;
@Slf4j
@Service
public class ChatGptServiceImpl implements ChatGptService {
    @Value("${openai.key}")
    private String openaiKey;
    @Override
    public void chatGptMsg(MessageEventParam messageEventParam) {
        String msgParam = messageEventParam.getMessage().replace("/tk","").trim();

        //国内访问需要做代理，国外服务器不需要
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .proxy(proxy)//自定义代理
                .addInterceptor(new OpenAiResponseInterceptor())//自定义返回值拦截
                .connectTimeout(10, TimeUnit.SECONDS)//自定义超时时间
                .writeTimeout(30, TimeUnit.SECONDS)//自定义超时时间
                .readTimeout(30, TimeUnit.SECONDS)//自定义超时时间
                .build();
        //构建客户端
        OpenAiClient openAiClient = OpenAiClient.builder()
                .apiKey(Arrays.asList(openaiKey))
                //自定义key的获取策略：默认KeyRandomStrategy
                .keyStrategy(new KeyRandomStrategy())
                //.keyStrategy(new FirstKeyStrategy())
                .okHttpClient(okHttpClient)
                //自己做了代理就传代理地址，没有可不不传
//                .apiHost("https://自己代理的服务器地址/")
                .build();
        //聊天模型：gpt-3.5
        Message message = Message.builder().role(Message.Role.USER).content(msgParam).build();
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
        StringBuilder msg = new StringBuilder();
        chatCompletionResponse.getChoices().forEach(e -> {
            msg.append(e.getMessage().getContent());
        });
        if(messageEventParam.getGroup_id()!=0){
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false );
        }else {
            sendPrivateMsg(messageEventParam.getUser_id(), String.valueOf(msg),false);
        }
    }

    @Override
    public void aiDraw(MessageEventParam messageEventParam) {
        String hint = messageEventParam.getMessage().replace("/aipic","").trim();
        System.out.println(messageEventParam);
        String msgParam = messageEventParam.getMessage().replace("/tk","");
        //国内访问需要做代理，国外服务器不需要
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .proxy(proxy)//自定义代理
                .addInterceptor(new OpenAiResponseInterceptor())//自定义返回值拦截
                .connectTimeout(10, TimeUnit.SECONDS)//自定义超时时间
                .writeTimeout(30, TimeUnit.SECONDS)//自定义超时时间
                .readTimeout(30, TimeUnit.SECONDS)//自定义超时时间
                .build();
        //构建客户端
        OpenAiClient openAiClient = OpenAiClient.builder()
                .apiKey(Arrays.asList(openaiKey))
                //自定义key的获取策略：默认KeyRandomStrategy
                .keyStrategy(new KeyRandomStrategy())
                //.keyStrategy(new FirstKeyStrategy())
                .okHttpClient(okHttpClient)
                //自己做了代理就传代理地址，没有可不不传
//                .apiHost("https://自己代理的服务器地址/")
                .build();
        ImageResponse imageResponse;
        try{
             imageResponse = openAiClient.genImages(hint);
        }catch (Exception e){
            if(messageEventParam.getGroup_id()!=0){
                sendGroupMsg(messageEventParam.getGroup_id(),e.getMessage(),false );
            }else {
                sendPrivateMsg(messageEventParam.getUser_id(), e.getMessage(),false);
            }
            return;
        }
        String picUrl = imageResponse.getData().get(0).getUrl();
        LocalDateTime now = LocalDateTime.now();
        sendGroupPicture(now.toString(),picUrl, messageEventParam.getGroup_id(),hint ,false);
    }
}
