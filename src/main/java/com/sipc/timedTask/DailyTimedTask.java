package com.sipc.timedTask;

import com.sipc.timedTask.timedTaskFun.TimedSendGroupMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyTimedTask implements Runnable {
    @Autowired
    private TimedSendGroupMsgService timedSendGroupMsgService;
    @Scheduled(cron = "30 0 8 * * ?")
    @Scheduled(cron = "30 0 12 * * ?")
    @Scheduled(cron = "30 0 18 * * ?")
    @Scheduled(cron = "30 0 22 * * ?")
    @Override
    public void run() {
        timedSendGroupMsgService.timedSendGroupMsg();
    }
}