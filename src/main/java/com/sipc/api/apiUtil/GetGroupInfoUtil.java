package com.sipc.api.apiUtil;

import com.alibaba.fastjson.JSONObject;
import com.sipc.api.entity.groupMemberInfoParam.GroupMemberInfoData;
import com.sipc.api.entity.groupMemberInfoParam.GroupMemberInfoParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
@Slf4j
public class GetGroupInfoUtil {
    public static GroupMemberInfoParam getGroupMemberInfo(int group_id,long user_id,boolean no_cache){
        StringBuilder sb = new StringBuilder();
        sb.append("?").append("group_id=").append(group_id)
                .append("&").append("user_id=").append(user_id)
                .append("&").append("no_cache=").append(no_cache);
        try {
            URL url = new URL("http://127.0.0.1:8077/get_group_member_info"+sb);
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
            return JSONObject.parseObject(response, GroupMemberInfoData.class).getData();
        } catch (Exception e) {
            log.error("GetGroupInfoUtil err!");
            return null;
        }
    }
}
