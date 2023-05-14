package com.sipc.events.Service;

import com.sipc.events.entity.param.MessageEventParam;

public interface PetService {
    void adoptPet(MessageEventParam messageEventParam);
    void changeName(MessageEventParam messageEventParam);
    void confess(MessageEventParam messageEventParam);
    void promise(MessageEventParam messageEventParam);
    void hug(MessageEventParam messageEventParam);
    void punch(MessageEventParam messageEventParam);
    void breakUp(MessageEventParam messageEventParam);
    void fight(MessageEventParam messageEventParam);
    void selectGroupRank(MessageEventParam messageEventParam);
    void selectMyPet(MessageEventParam messageEventParam);
}
