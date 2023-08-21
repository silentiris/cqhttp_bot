package com.sipc.events.entity.param.oj.contestRank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ContestRankInfo {
    private long accepted_number;
    private long contest;
    private long id;
    private long submission_number;
    private long total_time;
    private ContestRankUser user;
}
