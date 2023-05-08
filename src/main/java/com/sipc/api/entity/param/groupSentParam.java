package com.sipc.api.entity.param;

import lombok.Data;

@Data
public class groupSentParam {
    private int group_id;
    private String message;
    private boolean auto_escape;
}
