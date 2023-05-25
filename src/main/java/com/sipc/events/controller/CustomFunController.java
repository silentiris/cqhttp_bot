package com.sipc.events.controller;

import com.sipc.events.Service.*;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.randomPictureParam.RandomPictureParam;
import com.sipc.timedTask.timedTaskFun.TimedSendGroupMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
    @Autowired
    private QueryWeatherService queryWeatherService;
    @Autowired
    private ChatGptService chatGptService;
    @Autowired
    private PetService petService;
    @Autowired
    private TimedSendGroupMsgService timedSendGroupMsgService;
    @Autowired
    private DriftingBottleService driftingBottleService;

    public void CustomFunHandler(MessageEventParam messageEventParam){
        String msg = messageEventParam.getMessage();
        boolean idGroup =  messageEventParam.getGroup_id()!=0;
        if (msg.startsWith("/help")){
            guideService.globalGuide(messageEventParam);
        }
        else if(msg.startsWith("/知乎日报")){
            dailyNewsService.dailyNews(messageEventParam);
        }
        else if(msg.startsWith("/历史上的今天")){
            todayInHistoryService.todayInHistory(messageEventParam);
        } else if(msg.startsWith("/天气说明")){
            guideService.weatherGuide(messageEventParam);
        }
        else if(msg.startsWith("/天气")){
            queryWeatherService.queryWeather(messageEventParam);
        }else if(msg.startsWith("/领养") && idGroup){
            petService.adoptPet(messageEventParam);
        }else if (msg.startsWith("/改名") && idGroup){
            petService.changeName(messageEventParam);
        }else if (msg.startsWith("/couple") && idGroup){
            petService.confess(messageEventParam);
        } else if (msg.startsWith("/同意") && idGroup) {
            petService.promise(messageEventParam);
        } else if (msg.startsWith("/贴贴") && idGroup) {
            petService.hug(messageEventParam);
        } else if (msg.startsWith("/早安") && idGroup) {
            petService.punch(messageEventParam);
        } else if (msg.startsWith("/分手") && idGroup) {
            petService.breakUp(messageEventParam);
        }else if(msg.startsWith("/比划比划") && idGroup){
            petService.fight(messageEventParam);
        }else if (msg.startsWith("/群兔兔排行") && idGroup){
            petService.selectGroupRank(messageEventParam);
        }else if (msg.startsWith("/我的tutu") && idGroup){
            petService.selectMyPet(messageEventParam);
        }
        else if(msg.startsWith("/兔兔说明")){
            guideService.petGuide(messageEventParam);
        }
        else if(msg.startsWith("兔兔规则")){
            guideService.petRuleGuide(messageEventParam);
        } else if(msg.startsWith("/tk")){
            chatGptService.chatGptMsg(messageEventParam);
        } else if (msg.startsWith("/aipic")) {
            chatGptService.aiDraw(messageEventParam);
        }else if (msg.startsWith("/throw")) {
            driftingBottleService.throwBottle(messageEventParam);
        }else if (msg.startsWith("/pick")) {
            driftingBottleService.pickBottle(messageEventParam);
        }else if(msg.startsWith("/picguide")){
            guideService.randomPictureGenerateGuide(messageEventParam);
        }
        else if(msg.startsWith("/pic")){
            randomPictureService.RandomPicture(messageEventParam);
        }else if(msg.startsWith("/cmt")){
            driftingBottleService.addBottleComment(messageEventParam);
        }else if(msg.startsWith("/showcmt")){
            driftingBottleService.selectComments(messageEventParam);
        }else if(msg.startsWith("/bottlehelp")){
            guideService.driftingBottleGuide(messageEventParam);
        }
    }
}
