package com.sipc;

import com.sipc.api.apiUtil.MinioUtil;
import com.sipc.timedTask.timedTaskFun.TimedSendGroupMsgService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QqBotHttpApplicationTests {
    @Autowired
    private TimedSendGroupMsgService timedSendGroupMsgService;
    @Autowired
    private MinioUtil minioUtil;
    @Test
    void contextLoads() {
    }
    @Test
    void sendNight(){
        timedSendGroupMsgService.sendNightGroupMsg();
    }
    @Test
    void minio(){
       minioUtil.uploadFileWithNetFile("https://wx4.sinaimg.cn/orj360/002aEpz3ly1hf6yosbcd1j60ty18kwm002.jpg");
    }
}
