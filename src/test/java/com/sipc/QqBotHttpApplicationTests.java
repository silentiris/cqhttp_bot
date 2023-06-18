package com.sipc;

import com.sipc.timedTask.timedTaskFun.TimedSendGroupMsgService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QqBotHttpApplicationTests {
    @Autowired
    private TimedSendGroupMsgService timedSendGroupMsgService;
    @Test
    void contextLoads() {
    }
    @Test
    void sendNight(){
        timedSendGroupMsgService.sendMorningGroupMsg();
    }

}
