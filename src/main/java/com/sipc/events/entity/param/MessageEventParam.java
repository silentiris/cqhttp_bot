package com.sipc.events.entity.param;

import lombok.Data;

@Data
public class MessageEventParam {
    private String message_type;
    private String sub_type;
    private int message_id;
    private String message;
    private long user_id;
    private String raw_message;
    private int front;
    private int group_id;
    private Sender sender;

}
