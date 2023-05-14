package com.sipc.events.Service.impl;

import com.sipc.events.Service.GuideService;
import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.stereotype.Service;

import java.lang.management.GarbageCollectorMXBean;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
@Service
public class GuideServiceImpl implements GuideService {
    public void globalGuide(MessageEventParam messageEventParam) {
        String msg = """
                你好，我是一个qqBot；
                如果需要我，请@我，并带上你所需要的请求的名称。
                例：@tuTubot 知乎日报
                功能：
                1.知乎日报 2.历史上的今天
                3.搜图（输入“搜图说明”了解请求格式）
                4.天气（输入“天气说明”了解请求格式）
                5.兔兔（输入“兔兔说明”了解请求格式）
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
    public void petGuide(int group_id){
        String msg = """
                指令列表：
                @tuTubot 兔兔 领养 tuTu （领养一个名为tuTu的兔兔）
                @tuTubot 兔兔 改名 miemie （把自己的兔兔名字改为miemie）
                @tuTubot 兔兔 couple @woofwoof（申请和woofwoof建立亲密联系）（必须直接@，不可以复制）
                @tuTubot 兔兔 同意 @丿專傷伱の吢（同意丿專傷伱の吢的告白）（必须直接@，不可以复制）
                @tuTubot 兔兔 贴贴 （和couple贴贴，两个人都可以变大变强）
                @tuTubot 兔兔 早安 （早上签到，仅限5：30到8：30）
                @tuTubot 兔兔 分手（和你的couple分手，不需另一半同意）
                @tuTubot 兔兔 比划比划 @woofwoof（和woofwoof比划一下）
                @tuTubot 兔兔 群兔兔排行 （查看群tutu的排行榜）
                @tuTubot 兔兔 我的tutu （查看自己的tutu信息）
                
                @tuTubot 兔兔 规则 （查看规则）
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
