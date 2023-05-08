package com.sipc.events.entity.param.randomPictureParam;

import lombok.Data;

import java.util.List;
@Data
public class RandomPictureData {
    private int pid;
    private String title;
    private String author;
    private String width;
    private String height;
    private List<String> tags;
    private RandomPictureDataUrls urls;

}
