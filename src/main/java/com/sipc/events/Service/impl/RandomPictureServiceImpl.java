package com.sipc.events.Service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sipc.events.Service.RandomPictureService;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.param.randomPictureParam.RandomPictureData;
import com.sipc.events.entity.param.randomPictureParam.RandomPictureParam;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendPictureUtil.sendPicture;
import static com.sipc.common.eventCommon.FunParam.PICTURE_URL;
import static com.sipc.common.eventCommon.FunParam.RANDOMPIC_FALSE;
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;
@Service
public class RandomPictureServiceImpl implements RandomPictureService {
    public void RandomPicture(MessageEventParam messageEventParam){
        RandomPictureParam randomPictureParam = null;
        if(messageEventParam.getMessage().startsWith("/picjson")){
            String jsonParam = messageEventParam.getMessage().replace("/picjson","");
            jsonParam = StringEscapeUtils.unescapeHtml4(jsonParam);
            System.out.println(jsonParam);
            try {
                URL url = new URL(PICTURE_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                connection.setDoOutput(true); // 允许写入请求体
                // 设置请求体数据
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(jsonParam.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
                // 读取响应内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();
                String response = responseBuilder.toString();
                randomPictureParam = JSONObject.parseObject(response, RandomPictureParam.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            String tag = messageEventParam.getMessage();
            tag = tag.substring(tag.indexOf("c") + 1);
            tag = tag.replaceAll("amp;", "");
            List<String> tagList = List.of(tag.split("，"));
            if (tagList.size() > 3) {
                sendGroupMsg(messageEventParam.getGroup_id(), "参数有误", false);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("?");
                for (int i = 0; i < tagList.size(); i++) {
                    sb.append("tag=").append(URLEncoder.encode(tagList.get(i), StandardCharsets.UTF_8));
                    if (i != tagList.size() - 1) {
                        sb.append("&");
                    }
                }
                sb.append("&num=10");
                String url;
                if (tag.equals("")) {
                    url = PICTURE_URL;
                } else {
                    url = PICTURE_URL + sb;
                }
                randomPictureParam = JSONObject.parseObject(sendHttpRequest(url, true), RandomPictureParam.class);
            }
        }
            boolean  isGetPicture = true;
            if(randomPictureParam.getData().size()==0){
                sendGroupMsg(messageEventParam.getGroup_id(), "未找到图片",false);
                isGetPicture = false;
            }
            if(isGetPicture){
                int index = (int) (randomPictureParam.getData().size()*Math.random());
                if(index==randomPictureParam.getData().size()){index--;}
                RandomPictureData randomPictureData = randomPictureParam.getData().get(index);
                String pictureUrl = randomPictureData.getUrls().getOriginal();
                String fileName = String.valueOf(randomPictureData.getPid());
                StringBuilder msg = new StringBuilder();
                msg.append("Title: ").append(randomPictureData.getTitle()).append("\n");
                msg.append("tags: ");
                for(int i = 0;i < randomPictureData.getTags().size();i++){
                    if(i != randomPictureData.getTags().size()-1){
                        msg.append(randomPictureData.getTags().get(i)).append(",");
                    }
                }
                msg.append("\n");
                if(sendPicture(fileName,pictureUrl, messageEventParam.getGroup_id(), String.valueOf(msg),false)==RANDOMPIC_FALSE){
                    sendGroupMsg(messageEventParam.getGroup_id(),"图片发送失败",false);
                }
            }
        }
    }

