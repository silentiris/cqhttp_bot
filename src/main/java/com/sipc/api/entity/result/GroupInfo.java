package com.sipc.api.entity.result;

import lombok.Data;

@Data
public class GroupInfo {
    private int group_id;
    private String group_name;
    private String group_memo;
    private int group_create_time;
    private int group_level;
    private int member_count;
    private int max_member_count;
}
