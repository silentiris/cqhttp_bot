package com.sipc.api.entity.groupMemberInfoParam;

import lombok.Data;

@Data
public class GroupMemberInfoParam {
    private int group_id;
    private long user_id;
    private String nickname; //昵称
    private String card; //群名片／备注
    private String sex;
    private int age;
    private String area;
    private int join_time; //加群时间戳
    private int last_sent_time; //	最后发言时间戳
    private String level;
    private String role;
}
