package com.sipc.api.entity.result.getGroupListParam;

import com.sipc.api.entity.result.GroupInfo;
import lombok.Data;

import java.util.List;

@Data
public class GetGroupListResult {
    private List<GroupInfo> data;
}
