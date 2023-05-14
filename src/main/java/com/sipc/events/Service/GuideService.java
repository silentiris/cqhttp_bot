package com.sipc.events.Service;

import com.sipc.events.entity.param.MessageEventParam;

public interface GuideService {
    void globalGuide(MessageEventParam messageEventParam);
    void randomPictureGenerateGuide(int group_id);
    void weatherGuide(int group_id);
    void petGuide(int group_id);
    void petRuleGuide(int group_id);
}
