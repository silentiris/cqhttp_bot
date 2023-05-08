package com.sipc.events.controller;

import com.sipc.events.Service.DailyNewsService;
import com.sipc.events.Service.GuideService;
import com.sipc.events.Service.RandomPictureService;
import com.sipc.events.Service.TodayInHistoryService;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.randomPictureParam.RandomPictureParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static com.sipc.common.globalCommon.GlobalCommon.BOT_ID;

@Controller
public class CustomFunController {
    @Autowired
    private GuideService guideService;
    @Autowired
    private DailyNewsService dailyNewsService;
    @Autowired
    private TodayInHistoryService todayInHistoryService;
    @Autowired
    private RandomPictureService randomPictureService;
    public void CustomFunHandler(MessageEventParam messageEventParam){
        if (messageEventParam.getMessage().equals("[CQ:at,qq="+BOT_ID+"] ")) guideService.globalGuide(messageEventParam);
        if(messageEventParam.getMessage().equals("[CQ:at,qq="+BOT_ID+"] 1")) dailyNewsService.dailyNews(messageEventParam);
        if(messageEventParam.getMessage().equals("[CQ:at,qq="+BOT_ID+"] 2")) todayInHistoryService.todayInHistory(messageEventParam);
        if(messageEventParam.getMessage().contains("[CQ:at,qq="+BOT_ID+"] 3.1")) randomPictureService.RandomPicture(messageEventParam);
        if(messageEventParam.getMessage().equals("[CQ:at,qq="+BOT_ID+"] 3.2")) guideService.randomPictureGenerateGuide(messageEventParam.getGroup_id());
    }
}
