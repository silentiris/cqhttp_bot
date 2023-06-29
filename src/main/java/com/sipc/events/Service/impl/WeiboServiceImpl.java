package com.sipc.events.Service.impl;

import com.sipc.api.apiUtil.MinioUtil;
import com.sipc.events.Service.WeiboService;
import com.sipc.events.entity.param.WeiboParam;
import jdk.dynalink.linker.LinkerServices;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
@Slf4j
@Service
public class WeiboServiceImpl implements WeiboService {
    @Autowired
    private MinioUtil minioUtil;
    @Override
    public void syncWeibo(WeiboParam weiboParam) {
        StringBuilder msg = new StringBuilder();
        System.out.println(weiboParam);
        msg.append(weiboParam.getContent());
        List<String> picNames = new ArrayList<>();
        if(weiboParam.getPicUrls().size()!=0){
            for(String url : weiboParam.getPicUrls()){
                String urlName = minioUtil.uploadFileWithNetFile(url);
                picNames.add(urlName);
                msg.append("[CQ:image,file=").append(urlName).append(",subType=0,url=").append(minioUtil.downloadFile(urlName)).append("]");
            }
        }
        sendGroupMsg(223885758, String.valueOf(msg),false);
        log.info("sync weibo!");
        for(String picName : picNames){
            minioUtil.removeObject(picName);
        }
    }
}
