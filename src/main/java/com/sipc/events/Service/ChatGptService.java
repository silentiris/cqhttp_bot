package com.sipc.events.Service;

import com.sipc.events.entity.param.MessageEventParam;

public interface ChatGptService {
    void chatGptMsg(MessageEventParam messageEventParam);
    void aiDraw(MessageEventParam messageEventParam);
    void autoGpt(MessageEventParam messageEventParam);
}
