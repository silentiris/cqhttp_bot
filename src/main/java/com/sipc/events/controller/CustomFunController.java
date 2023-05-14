package com.sipc.events.controller;

import com.sipc.events.Service.*;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.randomPictureParam.RandomPictureParam;
import com.sipc.timedTask.timedTaskFun.TimedSendGroupMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.sipc.common.globalCommon.GlobalCommon.BOT_ID;
import static com.sipc.common.globalCommon.GlobalCommon.BOT_NAME;

@Controller
public class CustomFunController {
    @Autowired
    private PetController petController;
    @Autowired
    private GuideService guideService;
    @Autowired
    private DailyNewsService dailyNewsService;
    @Autowired
    private TodayInHistoryService todayInHistoryService;
    @Autowired
    private RandomPictureService randomPictureService;
    @Autowired
    private QueryWeatherService queryWeatherService;

    public void CustomFunHandler(MessageEventParam messageEventParam){
        String msg = messageEventParam.getMessage();
        if (msg.equals("[CQ:at,qq="+BOT_ID+"]")||msg.equals("@"+BOT_NAME)){
            guideService.globalGuide(messageEventParam);
        }
        else if(msg.equals("[CQ:at,qq="+BOT_ID+"]知乎日报")||msg.equals("@"+BOT_NAME+"知乎日报")){
            dailyNewsService.dailyNews(messageEventParam);
        }
        else if(msg.equals("[CQ:at,qq="+BOT_ID+"]历史上的今天")||msg.equals("@"+BOT_NAME+"历史上的今天")){
            todayInHistoryService.todayInHistory(messageEventParam);
        }
        else if(msg.contains("[CQ:at,qq="+BOT_ID+"]搜图")||msg.contains("@"+BOT_NAME+"搜图")){
            randomPictureService.RandomPicture(messageEventParam);
        }
        else if(msg.contains("[CQ:at,qq="+BOT_ID+"]搜图说明")||msg.contains("@"+BOT_NAME+"搜图说明")){
            guideService.randomPictureGenerateGuide(messageEventParam.getGroup_id());
        }
        else if(msg.equals("[CQ:at,qq="+BOT_ID+"]天气说明")||msg.contains("@"+BOT_NAME+"天气说明")){
            guideService.weatherGuide(messageEventParam.getGroup_id());
        }
        else if((msg.contains("[CQ:at,qq="+BOT_ID+"]天气")||msg.contains("@"+BOT_NAME+"天气"))&&!(msg.contains("[CQ:at,qq="+BOT_ID+"]天气说明")||msg.contains("@"+BOT_NAME+"天气说明"))){
            queryWeatherService.queryWeather(messageEventParam);
        }
        else if(msg.equals("[CQ:at,qq="+BOT_ID+"]兔兔说明")||msg.contains("@"+BOT_NAME+"兔兔说明")){
            guideService.petGuide(messageEventParam.getGroup_id());
        }
        else if(msg.equals("[CQ:at,qq="+BOT_ID+"]兔兔规则")||msg.contains("@"+BOT_NAME+"兔兔规则")){
            guideService.petRuleGuide(messageEventParam.getGroup_id());
        }
        else if(msg.contains("[CQ:at,qq="+BOT_ID+"]兔兔")||msg.contains("@"+BOT_NAME+"兔兔")){
            petController.petHandler(messageEventParam);
        }
    }
}
