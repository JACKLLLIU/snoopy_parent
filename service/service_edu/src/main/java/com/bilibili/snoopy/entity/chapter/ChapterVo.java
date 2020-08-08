package com.bilibili.snoopy.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {
    private String id;
    private String title;

    //每个章节由多个视频组成
    private List<VideoVo> children = new ArrayList<VideoVo>();
}
