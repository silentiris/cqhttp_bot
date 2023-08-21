package com.sipc.events.Service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sipc.api.entity.param.forwardMsg.ForwardNode;
import com.sipc.api.entity.param.forwardMsg.ForwardNodeData;
import com.sipc.events.Service.CacheService;
import com.sipc.events.Service.OjService;
import com.sipc.events.entity.param.oj.contestRank.ContestRankInfo;
import com.sipc.events.entity.param.oj.contestRank.ContestRankParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sipc.api.apiUtil.SendForwardMsgUtil.sendForwardMsg;
import static com.sipc.common.utilCommon.SendHttpRequestUtil.sendHttpRequest;

@Service
@Slf4j
public class OjServiceImpl implements OjService {
    @Autowired
    private CacheService cacheService;

    @Override
    public boolean showContestRank(int group_id) {
        List<ForwardNode> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String sessionId = cacheService.getOjSessionId();
        try {
            StringBuilder sb = new StringBuilder();
            ContestRankParam contestRankParam = objectMapper
                    .readValue(sendHttpRequest("https://code.sipcoj.com/api/contest_rank?offset=0&limit=200&contest_id=47",
                            "Cookie", "sessionid=" + sessionId, true), ContestRankParam.class);
            List<ContestRankInfo> verifyUsers = new ArrayList<>();
            for (ContestRankInfo contestRankInfo : contestRankParam.getData().getResults()) {
                if (contestRankInfo.getUser().getUsername().startsWith("23-")) {
                    verifyUsers.add(contestRankInfo);
                }
            }
            List<ContestRankInfo> akUsers = new ArrayList<>();
            for (ContestRankInfo contestRankInfo : verifyUsers) {
                if (contestRankInfo.getAccepted_number() == 5) {
                    akUsers.add(contestRankInfo);
                }
            }
            sb.append("task1").append("\n")
                    .append("总人数：").append(verifyUsers.size()).append("\n")
                    .append("ak人数").append(akUsers.size()).append("\n")
                    .append("ak名单：").append("\n");
            for (ContestRankInfo contestRankInfo : akUsers) {
                sb.append(contestRankInfo.getUser().getUsername()).append(" ");
            }
            sb.append("\n");
            ForwardNode forwardNode = new ForwardNode();
            forwardNode.setType("node");
            ForwardNodeData forwardNodeData = new ForwardNodeData();
            forwardNodeData.setUin("3416214688");
            forwardNodeData.setContent(sb.toString());
            forwardNodeData.setName("猫猫");
            forwardNode.setData(forwardNodeData);
            list.add(forwardNode);
        } catch (Exception e) {
            cacheService.putOjSessionId();
            log.error("showContestRank() t1 err!");
            return false;
        }

        try {
            StringBuilder sb = new StringBuilder();
            ContestRankParam contestRankParam = objectMapper
                    .readValue(sendHttpRequest("https://code.sipcoj.com/api/contest_rank?offset=0&limit=200&contest_id=48",
                            "Cookie", "sessionid=" + sessionId, true), ContestRankParam.class);
            List<ContestRankInfo> verifyUsers = new ArrayList<>();
            for (ContestRankInfo contestRankInfo : contestRankParam.getData().getResults()) {
                if (contestRankInfo.getUser().getUsername().startsWith("23-")) {
                    verifyUsers.add(contestRankInfo);
                }
            }
            List<ContestRankInfo> akUsers = new ArrayList<>();
            for (ContestRankInfo contestRankInfo : verifyUsers) {
                if (contestRankInfo.getAccepted_number() == 5) {
                    akUsers.add(contestRankInfo);
                }
            }
            sb.append("task2").append("\n")
                    .append("总人数：").append(verifyUsers.size()).append("\n")
                    .append("ak人数").append(akUsers.size()).append("\n")
                    .append("ak名单：").append("\n");
            for (ContestRankInfo contestRankInfo : akUsers) {
                sb.append(contestRankInfo.getUser().getUsername()).append(" ");
            }
            sb.append("\n");
            ForwardNode forwardNode = new ForwardNode();
            forwardNode.setType("node");
            ForwardNodeData forwardNodeData = new ForwardNodeData();
            forwardNodeData.setUin("3416214688");
            forwardNodeData.setContent(sb.toString());
            forwardNodeData.setName("猫猫");
            forwardNode.setData(forwardNodeData);
            list.add(forwardNode);
        } catch (Exception e) {
            log.error("showContestRank() t2 err!");
            return false;
        }

        try {
            StringBuilder sb = new StringBuilder();
            ContestRankParam contestRankParam = objectMapper
                    .readValue(sendHttpRequest("https://code.sipcoj.com/api/contest_rank?offset=0&limit=200&contest_id=49",
                            "Cookie", "sessionid=" + sessionId, true), ContestRankParam.class);
            List<ContestRankInfo> verifyUsers = new ArrayList<>();
            for (ContestRankInfo contestRankInfo : contestRankParam.getData().getResults()) {
                if (contestRankInfo.getUser().getUsername().startsWith("23-")) {
                    verifyUsers.add(contestRankInfo);
                }
            }
            List<ContestRankInfo> akUsers = new ArrayList<>();
            for (ContestRankInfo contestRankInfo : verifyUsers) {
                if (contestRankInfo.getAccepted_number() == 5) {
                    akUsers.add(contestRankInfo);
                }
            }
            sb.append("task3").append("\n")
                    .append("总人数：").append(verifyUsers.size()).append("\n")
                    .append("ak人数").append(akUsers.size()).append("\n")
                    .append("ak名单：").append("\n");
            for (ContestRankInfo contestRankInfo : akUsers) {
                sb.append(contestRankInfo.getUser().getUsername()).append(" ");
            }
            sb.append("\n");
            ForwardNode forwardNode = new ForwardNode();
            forwardNode.setType("node");
            ForwardNodeData forwardNodeData = new ForwardNodeData();
            forwardNodeData.setUin("3416214688");
            forwardNodeData.setContent(sb.toString());
            forwardNodeData.setName("猫猫");
            forwardNode.setData(forwardNodeData);
            list.add(forwardNode);
        } catch (JsonProcessingException e) {
            log.error("showContestRank() t3 err!");
            return false;
        }
        try {
            StringBuilder sb = new StringBuilder();
            ContestRankParam contestRankParam = objectMapper
                    .readValue(sendHttpRequest("https://code.sipcoj.com/api/contest_rank?offset=0&limit=200&contest_id=50",
                            "Cookie", "sessionid=" + sessionId, true), ContestRankParam.class);
            List<ContestRankInfo> verifyUsers = new ArrayList<>();
            for (ContestRankInfo contestRankInfo : contestRankParam.getData().getResults()) {
                if (contestRankInfo.getUser().getUsername().startsWith("23-")) {
                    verifyUsers.add(contestRankInfo);
                }
            }
            List<ContestRankInfo> akUsers = new ArrayList<>();
            for (ContestRankInfo contestRankInfo : verifyUsers) {
                if (contestRankInfo.getAccepted_number() == 5) {
                    akUsers.add(contestRankInfo);
                }
            }
            sb.append("task4").append("\n")
                    .append("总人数：").append(verifyUsers.size()).append("\n")
                    .append("ak人数").append(akUsers.size()).append("\n")
                    .append("ak名单：").append("\n");
            for (ContestRankInfo contestRankInfo : akUsers) {
                sb.append(contestRankInfo.getUser().getUsername()).append(" ");
            }
            sb.append("\n");
            ForwardNode forwardNode = new ForwardNode();
            forwardNode.setType("node");
            ForwardNodeData forwardNodeData = new ForwardNodeData();
            forwardNodeData.setUin("3416214688");
            forwardNodeData.setContent(sb.toString());
            forwardNodeData.setName("猫猫");
            forwardNode.setData(forwardNodeData);
            list.add(forwardNode);
        } catch (JsonProcessingException e) {
            log.error("showContestRank() t4 err!");
            return false;
        }
        sendForwardMsg(group_id, list);
        return true;
    }
}
