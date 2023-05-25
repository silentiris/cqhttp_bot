package com.sipc.api.apiUtil;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.groupMemberInfoParam.GroupMemberInfoData;
import com.sipc.api.entity.userInfoParam.UserInfoData;
import com.sipc.api.entity.userInfoParam.UserInfoParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetUserInfoUtil {
    public static UserInfoParam getUserInfo(long user_id,boolean no_cache){
        StringBuilder sb = new StringBuilder();
        sb.append("?").append("&").append("user_id=").append(user_id)
                .append("&").append("no_cache=").append(no_cache);
        try {
            URL url = new URL("http://127.0.0.1:8077/get_stranger_info"+sb);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/json;charset=utf-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String response = responseBuilder.toString();
            return JSONObject.parseObject(response, UserInfoData.class).getData();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
