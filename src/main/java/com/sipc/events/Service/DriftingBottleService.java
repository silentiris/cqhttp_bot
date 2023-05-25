package com.sipc.events.Service;

import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.cglib.core.MethodWrapper;

import java.awt.*;

public interface DriftingBottleService {
    void throwBottle(MessageEventParam messageEventParam);
    void pickBottle(MessageEventParam messageEventParam);
    void addBottleComment(MessageEventParam messageEventParam);
    void selectComments(MessageEventParam messageEventParam);

}
