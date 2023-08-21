package com.sipc.events.entity.param.oj.contestRank;

import lombok.Data;

import java.util.List;

@Data
public class ContestRankData {
    private List<ContestRankInfo> results;
    private long total;
}
