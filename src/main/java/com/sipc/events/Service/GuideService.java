package com.sipc.events.Service;

import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.stereotype.Service;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
@Service
public class GuideService {
    public void globalGuide(MessageEventParam messageEventParam) {
        String msg = """
                你好，我是一个qqBot；
                如果需要我，请@我，并带上你所需要的请求的代码。
                例：@忠国兔兔 1
                功能：
                1.每日新闻 2.历史上的今天
                3.1随机图片生成（输入3.2了解请求格式）
                """;
        sendGroupMsg(messageEventParam.getGroup_id(), msg, false);
    }
    public void randomPictureGenerateGuide(int group_id){
        String msg = """       
               例：若想查找“(吴京或华为)的(星穹轨道或守望先锋)的图片”，即 (吴京 OR 华为) AND (星穹轨道 OR 守望先锋)，那么可以这样发送请求
                @忠国兔兔 3.1 萝莉，原神，臭脚体育生
                注：每个部分之前一个空格，逗号为中文逗号，
                tag为并列关系，最多三个tag。
                """;
        sendGroupMsg(group_id, msg, false);
    }
}
