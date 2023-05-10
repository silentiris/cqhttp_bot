package com.sipc.events.entity.param.DailyNewsParam;

import ch.qos.logback.core.read.ListAppender;
import lombok.Data;

import java.util.List;


@Data
public class DailyNewsParam {
    private List<NewsData> news;
    private List<TopStoriesData> top_stories;
    private String display_date;
}
