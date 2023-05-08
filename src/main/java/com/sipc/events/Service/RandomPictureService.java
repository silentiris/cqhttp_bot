package com.sipc.events.Service;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.TodayInHistoryParam;
import com.sipc.events.entity.param.randomPictureParam.RandomPictureParam;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendPictureUtil.sendPicture;
import static com.sipc.common.eventCommon.FunParam.PICTURE_URL;

@Service
public class RandomPictureService {
    public void RandomPicture(MessageEventParam messageEventParam){
        String tag = messageEventParam.getMessage().replaceAll("\\s", "").replace("[CQ:at,qq=2551600140]3.1", "");
        tag = tag.replaceAll("amp;","");
        List<String> tagList = List.of(tag.split("，"));
        if(tagList.size()>3){sendGroupMsg(messageEventParam.getGroup_id(),"参数有误",false);}
        else {
            StringBuilder sb = new StringBuilder();
            sb.append("?");
            for(int i = 0;i< tagList.size();i++) {
                sb.append("tag=").append(tagList.get(i));
                if(i!= tagList.size()-1){sb.append("&");}
            }
            RandomPictureParam randomPictureParam = null;
            boolean isGetPicture = false;
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = null;
            if(tag.equals("")){
                httpGet = new HttpGet(PICTURE_URL);
            }else {
                httpGet = new HttpGet(PICTURE_URL+sb);
            }
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    isGetPicture = true;
                    randomPictureParam = JSONObject.parseObject(EntityUtils.toString(responseEntity), RandomPictureParam.class);
                    if(randomPictureParam.getData().size()==0){
                        sendGroupMsg(messageEventParam.getGroup_id(), "未找到图片",false);
                        isGetPicture = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpClient != null) {
                        httpClient.close();
                    }
                    if (response != null) {
                        response.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(isGetPicture){
                assert randomPictureParam != null;
                String pictureUrl = randomPictureParam.getData().get(0).getUrls().getOriginal();
                String fileName = String.valueOf(randomPictureParam.getData().get(0).getPid());
                sendPicture(fileName,pictureUrl, messageEventParam.getGroup_id(), false);
            }
        }
    }
}
