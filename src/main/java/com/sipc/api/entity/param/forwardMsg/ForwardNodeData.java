package com.sipc.api.entity.param.forwardMsg;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ForwardNodeData {
    private String uin;
    private String content;
    private String name;
}
