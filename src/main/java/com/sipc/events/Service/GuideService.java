package com.sipc.events.Service;

import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.stereotype.Service;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
@Service
public class GuideService {
    public void globalGuide(MessageEventParam messageEventParam) {
        String msg = """
                你好，我是一个qqBot；
                如果需要我，请@我，并带上你所需要的请求的名称。
                例：@忠国兔兔 知乎日报
                功能：
                1.知乎日报 2.历史上的今天
                3.搜图（输入“搜图说明”了解请求格式）
                4.天气（输入“天气说明”了解请求格式）
                """;
        sendGroupMsg(messageEventParam.getGroup_id(), msg, false);
    }
    public void randomPictureGenerateGuide(int group_id){
        String msg = """       
               例：
                @忠国兔兔 搜图 萝莉，原神
                注：每个部分之前一个空格，逗号为中文逗号，只能两个关键词之间加逗号
                tag为并列关系，最多三个tag。
                """;
        sendGroupMsg(group_id, msg, false);
    }
    public void weatherGuide(int group_id){
        String msg = """
                例：
                @忠国兔兔 天气 北京 （今天的北京天气）
                @忠国兔兔 天气 北京,星期二（一周以内的北京某天的天气）
                @忠国兔兔 天气 北京,未来七天（一周以内的北京的天气）
                """;
        sendGroupMsg(group_id,msg,false);
    }
}
