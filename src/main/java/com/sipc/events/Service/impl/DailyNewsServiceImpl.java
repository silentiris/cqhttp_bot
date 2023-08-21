package com.sipc.events.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.Service.DailyNewsService;
import com.sipc.events.entity.param.DailyNewsParam.DailyNewsParam;
import com.sipc.events.entity.param.DailyNewsParam.NewsData;
import com.sipc.events.entity.param.DailyNewsParam.TopStoriesData;
import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.stereotype.Service;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendMsgUtil.sendPrivateMsg;
import static com.sipc.common.eventCommon.FunParam.NEWS_URL;
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;
@Service
public class DailyNewsServiceImpl implements DailyNewsService {
    @Override
    public void dailyNews(MessageEventParam messageEventParam) {
        String json= sendHttpRequest(NEWS_URL,true);
        assert json != null;
        json = json.substring(json.indexOf("{"),json.lastIndexOf("}")+1);
        DailyNewsParam dailyNewsParam = JSONObject.parseObject(json, DailyNewsParam.class);
        StringBuilder param = new StringBuilder();
        param.append("知乎日报：\n");
        param.append("news:\n");
        for (NewsData newsData:dailyNewsParam.getNews()){
            param.append(newsData.getTitle()).append("\n").append(newsData.getShare_url()).append("\n");
        }
        param.append("top stories:\n");
        for(TopStoriesData topStoriesData: dailyNewsParam.getTop_stories()){
            param.append(topStoriesData.getTitle()).append("\n").append(topStoriesData.getShare_url()).append("\n");
        }
        if(messageEventParam.getGroup_id()!=0){
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(param),false );
        }else {
            sendPrivateMsg(messageEventParam.getUser_id(), String.valueOf(param),false);
        }
    }
}
