package com.bilibili.snoopy.service;

import com.bilibili.snoopy.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.snoopy.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author LJF
 * @since 2020-05-28
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getchapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeByCourseId(String courseId);
}
