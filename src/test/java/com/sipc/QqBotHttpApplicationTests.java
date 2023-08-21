package com.sipc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sipc.api.apiUtil.MinioUtil;
import com.sipc.events.Service.CacheService;
import com.sipc.events.Service.OjService;
import com.sipc.events.Util.spider.OjSpider;
import com.sipc.timedTask.KexieTimedTask;
import com.sipc.timedTask.timedTaskFun.TimedSendGroupMsgService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;

@SpringBootTest
class QqBotHttpApplicationTests {
    @Autowired
    private TimedSendGroupMsgService timedSendGroupMsgService;
    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private OjSpider ojSpider;
    @Autowired
    private OjService ojService;
    @Autowired
    private KexieTimedTask kexieTimedTask;
    @Test
    void contextLoads() throws InterruptedException, JsonProcessingException {
    }
    @Test
    void sendNight(){
        timedSendGroupMsgService.sendNightGroupMsg();
    }
    @Test
    void minio(){
       minioUtil.uploadFileWithNetFile("https://wx4.sinaimg.cn/orj360/002aEpz3ly1hf6yosbcd1j60ty18kwm002.jpg");
    }
    @Test
    void Test1() throws InterruptedException {
        System.out.println(ojSpider.getSessionId());
    }
    @Test
    void Test2(){
    }
}
