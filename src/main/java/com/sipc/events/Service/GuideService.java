package com.sipc.events.Service;

import com.sipc.events.entity.param.MessageEventParam;

public interface GuideService {
    void globalGuide(MessageEventParam messageEventParam);
    void randomPictureGenerateGuide(MessageEventParam messageEventParam);
    void weatherGuide(MessageEventParam messageEventParam);
    void petGuide(MessageEventParam messageEventParam);
    void petRuleGuide(MessageEventParam messageEventParam);

    void driftingBottleGuide(MessageEventParam messageEventParam);
}
