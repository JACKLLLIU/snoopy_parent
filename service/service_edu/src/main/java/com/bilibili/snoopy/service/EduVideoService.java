package com.bilibili.snoopy.service;

import com.bilibili.snoopy.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author LJF
 * @since 2020-05-28
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
