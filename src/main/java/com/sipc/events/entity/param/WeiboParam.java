package com.sipc.events.entity.param;

import lombok.Data;

import java.util.List;

@Data
public class WeiboParam {
    private String content;
    private List<String> picUrls;

}
