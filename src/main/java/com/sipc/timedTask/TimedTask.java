package com.sipc.timedTask;

import com.sipc.timedTask.timedTaskFun.TimedSendGroupMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class TimedTask implements Runnable {
    @Autowired
    private TimedSendGroupMsgService timedSendGroupMsgService;
    @Scheduled(cron = "0 0 8 * * ?")
    @Scheduled(cron = "0 0 22 * * ?")
    @Override
    public void run() {
        timedSendGroupMsgService.timedSendGroupMsg();
    }
}