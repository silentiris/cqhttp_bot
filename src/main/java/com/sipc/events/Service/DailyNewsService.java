package com.sipc.events.Service;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.entity.param.DailyNewsParam.DailyNewsParam;
import com.sipc.events.entity.param.DailyNewsParam.NewsData;
import com.sipc.events.entity.param.DailyNewsParam.TopStoriesData;
import com.sipc.events.entity.param.MessageEventParam;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.common.eventCommon.FunParam.NEWS_URL;
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;

@Service
public class DailyNewsService {
    public void dailyNews(MessageEventParam messageEventParam) {
        String json= sendHttpRequest(NEWS_URL);
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
        sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(param), false);
    }
}
