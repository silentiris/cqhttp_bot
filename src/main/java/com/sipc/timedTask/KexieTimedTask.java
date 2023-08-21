package com.sipc.timedTask;

import com.sipc.events.Service.OjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;

@Component
public class KexieTimedTask {
    @Autowired
    private OjService ojService;

    @Scheduled(cron = "0 0 8,14,20 * * ?")
    public void ctfMsg() {
        String msg = """
                对ctf感兴趣的同学请联系：张文轩 | 对CTF感兴趣私我
                """;
        sendGroupMsg(794097609, msg, false);
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void ojMsgMorning() {
        String msg = """
                 早上好！
                 该起床了，这是oj的通过情况，快来卷吧！！！
                """;
        int group_id = 794097609;
        sendGroupMsg(group_id, msg, false);
        if(!ojService.showContestRank(group_id)){
            ojService.showContestRank(group_id);
        }
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void ojMsgNight() {
        String msg = """
                 晚上好！
                 别睡觉了，这是oj的通过情况，快来卷吧！！！
                """;
        int group_id = 794097609;
        sendGroupMsg(group_id, msg, false);
        if(!ojService.showContestRank(group_id)){
            ojService.showContestRank(group_id);
        }
    }
}
