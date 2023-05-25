package com.sipc.events.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sipc.api.apiUtil.MinioUtil;
import com.sipc.events.Service.DriftingBottleService;
import com.sipc.events.dao.BottleCommentDao;
import com.sipc.events.dao.BottleDao;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.po.BottleCommentPo;
import com.sipc.events.entity.po.BottlePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;


import static com.sipc.api.apiUtil.GetGroupInfoUtil.getGroupMemberInfo;
import static com.sipc.api.apiUtil.GetUserInfoUtil.getUserInfo;
import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendMsgUtil.sendPrivateMsg;
import static com.sipc.api.apiUtil.SendPictureUtil.sendGroupPicture;
import static com.sipc.api.apiUtil.SendPictureUtil.sendPrivatePicture;

@Service
public class DriftingBottleServiceImpl implements DriftingBottleService {
    private BottleDao bottleDao;
    @Autowired
    public void setBottleDao(BottleDao bottleDao){
        this.bottleDao = bottleDao;
    }
    private MinioUtil minioUtil;
    @Autowired
    public void setMinioUtil(MinioUtil minioUtil){
        this.minioUtil = minioUtil;
    }
    private BottleCommentDao bottleCommentDao;
    @Autowired
    public void setBottleCommentDao(BottleCommentDao bottleCommentDao){
        this.bottleCommentDao = bottleCommentDao;
    }
    @Override
    public void throwBottle(MessageEventParam messageEventParam) {
        String message = messageEventParam.getMessage();
        message = message.replace("/throw","");
        String bottleMsg = message ;
        String picUrl = null;
        long throwerId = messageEventParam.getUser_id();
        boolean isGroup = messageEventParam.getGroup_id()!=0;
        int groupId = messageEventParam.getGroup_id();
        String fileName=  null;
        String throwerName = null;
        if(isGroup){
            throwerName = getGroupMemberInfo(groupId, throwerId, true).getNickname();
        }else{
            if(!("".equals(messageEventParam.getSender().getNickname()))){
                throwerName = messageEventParam.getSender().getNickname();
            }else {
                throwerName = getUserInfo(messageEventParam.getUser_id(),true).getNickname();
            }
        }
        if(message.contains("[CQ:image,")){
            bottleMsg = message.substring(0,message.indexOf("[CQ:image"));
            picUrl = message.substring(message.indexOf(",url=")+",url=".length(),message.length()-1);
            fileName = minioUtil.uploadFileWithNetFile(picUrl);
        }
        BottlePo bottlePo = new BottlePo();
        bottlePo.setGroupId(groupId);
        bottlePo.setThrowerId(throwerId);
        bottlePo.setThrowerName(throwerName);
        bottlePo.setBottleMsg(bottleMsg);
        if(message.contains("[CQ:image,")){
            bottlePo.setPhotoLink(fileName);
        }
        bottleDao.insert(bottlePo);
        if(isGroup){
            sendGroupMsg(groupId,"成功扔出漂流瓶！！\n"+"id: "+bottlePo.getId(),false);
        }else {
            sendPrivateMsg(messageEventParam.getUser_id(), "成功扔出漂流瓶！！\n"+"id: "+bottlePo.getId(),false);
        }
    }

    @Override
    public void pickBottle(MessageEventParam messageEventParam) {
        //取出所有不是自己扔的瓶子
        QueryWrapper<BottlePo> qw1 = new QueryWrapper<>();
        qw1.ne("thrower_id",messageEventParam.getUser_id());
        List<BottlePo> bottlePos = bottleDao.selectList(qw1);
        int pickIndex = (new Random()).nextInt(bottlePos.size());
        BottlePo bottlePo = bottlePos.get(pickIndex);
        boolean hasPic = !(bottlePo.getPhotoLink().equals("0"));
        QueryWrapper<BottleCommentPo> qw2 = new QueryWrapper<>();
        qw2.eq("bottle_id",bottlePo.getId());
        List<BottleCommentPo> bottleCommentPos = bottleCommentDao.selectList(qw2);
        int commentNum = bottleCommentPos.size();
        StringBuilder msg = new StringBuilder();
        msg.append(bottlePos.get(pickIndex).getThrowerName()).append("\n");
        msg.append("id: ").append(bottlePo.getId()).append("\n");
        msg.append("评论数量: ").append(commentNum).append("\n");
        msg.append(bottlePo.getBottleMsg());
        if(hasPic){
            if(messageEventParam.getGroup_id()!=0){
                sendGroupPicture(bottlePo.getPhotoLink().replace(".jpg",""),minioUtil.downloadFile(bottlePo.getPhotoLink()),messageEventParam.getGroup_id(), String.valueOf(msg),false);
            }else {
                sendPrivatePicture(bottlePo.getPhotoLink().replace(".jpg",""),minioUtil.downloadFile(bottlePo.getPhotoLink()),messageEventParam.getUser_id(), String.valueOf(msg),false);
            }
        }else {
            if(messageEventParam.getGroup_id()!=0){
                sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false);
            }else {
                sendPrivateMsg(messageEventParam.getUser_id(), String.valueOf(msg),false);
            }
        }

    }

    @Override
    public void addBottleComment(MessageEventParam messageEventParam) {
        String message = messageEventParam.getMessage();
        message = message.replace("/cmt","");
        int bottleId = -1;
        //检验是否输入了正确的数字
        try{
            bottleId = Integer.parseInt(message.substring(0,message.indexOf('/')));

        }catch (Exception e){
            if(messageEventParam.getGroup_id()!=0){
                sendGroupMsg(messageEventParam.getGroup_id(), "需要填入有效的瓶子id",false);
            }else {
                sendPrivateMsg(messageEventParam.getUser_id(), "需要填入有效的瓶子id",false);
            }
            return;
        }
        //检验是否有这个瓶子并取出
        QueryWrapper<BottlePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",bottleId);
        BottlePo bottlePo = bottleDao.selectOne(queryWrapper);
        if(null==bottlePo){
            if(messageEventParam.getGroup_id()!=0){
                sendGroupMsg(messageEventParam.getGroup_id(), "未找到该瓶子",false);
            }else {
                sendPrivateMsg(messageEventParam.getUser_id(), "未找到该瓶子",false);
            }
            return;
        }

        String commentMsg = message.replace(message.substring(0,message.indexOf('/')+1),"") ;
        String picUrl = null;
        long throwerId = messageEventParam.getUser_id();
        boolean isGroup = messageEventParam.getGroup_id()!=0;
        int groupId = messageEventParam.getGroup_id();
        String fileName=  null;
        String commenterName = null;
        if(isGroup){
            commenterName = getGroupMemberInfo(groupId, throwerId, true).getNickname();
        }else{
            if(!("".equals(messageEventParam.getSender().getNickname()))){
                commenterName = messageEventParam.getSender().getNickname();
            }else {
                commenterName = getUserInfo(messageEventParam.getUser_id(),true).getNickname();
            }
        }
        if(message.contains("[CQ:image,")){
            commentMsg = commentMsg.substring(0,message.indexOf("[CQ:image,")-3);
            picUrl = message.substring(message.indexOf(",url=")+",url=".length(),message.length()-1);
            fileName = minioUtil.uploadFileWithNetFile(picUrl);
        }
        //set date
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Date currentTime = new Date();
        String date = currentTime.toString();

        BottleCommentPo bottleCommentPo = new BottleCommentPo();
        bottleCommentPo.setBottleId(bottlePo.getId());
        bottleCommentPo.setCommentMsg(commentMsg);
        bottleCommentPo.setCommenterId(messageEventParam.getUser_id());
        bottleCommentPo.setCommenterName(commenterName);
        bottleCommentPo.setCommentPicUrl(fileName);
        bottleCommentPo.setDate(date);
        bottleCommentDao.insert(bottleCommentPo);
        if(messageEventParam.getGroup_id()!=0){
            sendGroupMsg(messageEventParam.getGroup_id(), commenterName+":已发出评论！",false);
        }else {
            sendPrivateMsg(messageEventParam.getUser_id(), commenterName+":已发出评论！",false);
        }
    }

    @Override
    public void selectComments(MessageEventParam messageEventParam) {
        String message = messageEventParam.getMessage();
        message = message.replace("/showcmt","");
        int bottleId ;
        //检验是否输入了正确的数字
        try{
            bottleId = Integer.parseInt(message);

        }catch (Exception e){
            if(messageEventParam.getGroup_id()!=0){
                sendGroupMsg(messageEventParam.getGroup_id(), "请填入有效的瓶子id",false);
            }else {
                sendPrivateMsg(messageEventParam.getUser_id(), "请要填入有效的瓶子id",false);
            }
            return;
        }
        QueryWrapper<BottleCommentPo> qw1 = new QueryWrapper<>();
        qw1.eq("bottle_id",bottleId);
        List<BottleCommentPo> bottleCommentPos = bottleCommentDao.selectList(qw1);
        if(bottleCommentPos==null||bottleCommentPos.size() == 0){
            if(messageEventParam.getGroup_id()!=0){
                sendGroupMsg(messageEventParam.getGroup_id(), "该漂流瓶不存在或还没有人评论",false);
            }else {
                sendPrivateMsg(messageEventParam.getUser_id(), "该漂流瓶不存在或还没有人评论",false);
            }
            return;
        }
        StringBuilder msg = new StringBuilder();
        msg.append("评论：").append("\n");
        for(BottleCommentPo bottleCommentPo:bottleCommentPos){
            msg.append(bottleCommentPo.getDate().substring(0,bottleCommentPo.getDate().indexOf("GMT+")-4)).append("\n");
            msg.append(bottleCommentPo.getCommenterName()).append(":").append("\n");
            msg.append(bottleCommentPo.getCommentMsg()).append("\n");
            if(!("0".equals(bottleCommentPo.getCommentPicUrl()))){
                msg.append("[CQ:image,file=").append(bottleCommentPo.getCommentPicUrl().replace(".jpg","")).append(",subType=0,url=").append(minioUtil.downloadFile(bottleCommentPo.getCommentPicUrl())).append("]").append("\n");
            }
            msg.append("\n");
        }
        if(messageEventParam.getGroup_id()!=0){
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false);
        }else {
            sendPrivateMsg(messageEventParam.getUser_id(), String.valueOf(msg),false);
        }
    }

}
