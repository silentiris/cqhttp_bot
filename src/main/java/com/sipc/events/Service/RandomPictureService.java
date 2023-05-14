package com.sipc.events.Service;

import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.stereotype.Service;


@Service
public interface RandomPictureService {
    void RandomPicture(MessageEventParam messageEventParam);
}
