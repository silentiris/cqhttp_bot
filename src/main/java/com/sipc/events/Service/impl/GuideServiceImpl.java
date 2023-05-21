package com.sipc.events.Service.impl;

import com.sipc.events.Service.GuideService;
import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.stereotype.Service;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
@Service
public class GuideServiceImpl implements GuideService {
    public void globalGuide(MessageEventParam messageEventParam) {
        String msg = """
                你好，我是一个qqBot；
                如果需要我，请按照下面的格式唤醒我。
                例：/tk 如何评价周杰伦？
                功能：
                1./知乎日报 2./历史上的今天
                3./pic（输入“/picguide”了解请求格式）
                4./天气（输入“/天气说明”了解请求格式）
                5./tk
                6./aipic (ai绘图)
                7. tutu类指令请参考"/兔兔说明"
                """;
        sendGroupMsg(messageEventParam.getGroup_id(), msg, false);
    }
    public void randomPictureGenerateGuide(int group_id){
        String msg = """       
               例：
                /pic 萝莉，原神
                注：每个部分之前一个空格，逗号为中文逗号，只能两个关键词之间加逗号
                tag为并列关系，最多三个tag。
                """;
        sendGroupMsg(group_id, msg, false);
    }
    public void weatherGuide(int group_id){
        String msg = """
                例：
                /天气 北京 （今天的北京天气）
                /天气 北京,星期二（一周以内的北京某天的天气）
                /天气 北京,未来七天（一周以内的北京的天气）
                """;
        sendGroupMsg(group_id,msg,false);
    }
    public void petGuide(int group_id){
        String msg = """
                指令列表：
                1./领养 tuTu （领养一个名为tuTu的兔兔）
                2./改名 miemie （把自己的兔兔名字改为miemie）
                3./couple @woof（申请和woof建立亲密联系）（必须直接@，不可以复制）
                4./同意 @cat（同意cat的告白）（必须直接@，不可以复制）
                5./贴贴 （和couple贴贴，两个人都可以变大变强）
                6./早安 （早上签到，仅限5：30到8：30）
                7./分手（和你的couple分手，不需另一半同意）
                8./比划比划 @woof（和woof比划一下）
                9./群兔兔排行 （查看群tutu的排行榜）
                10./我的tutu （查看自己的tutu信息）
                
                /兔兔规则 （查看规则）
                """;
        sendGroupMsg(group_id,msg,false);
    }

    public  void petRuleGuide(int group_id){
        String msg = """
                规则：
                1.鼓励早起，在规定时间段签到可以变强，越早变强越多。
                2.Couple tutu贴贴双方都可以变强。
                3.对于有couple的tutu，一个人签到可以获得一次贴贴机会（双方都可以贴），两个人签到可以获得三次贴贴机会。
                4.tutu可以找群里其他的tutu比划，体积小的tutu更不容易胜利，但胜利后会变强更多。
                输的一方会变小，胜利的一方会变大，也可能被晶叔叔发现，因为打架斗殴导致两败俱伤哦
                """;
        sendGroupMsg(group_id,msg,false);
    }
}
