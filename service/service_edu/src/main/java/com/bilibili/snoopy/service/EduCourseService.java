package com.bilibili.snoopy.service;

import com.bilibili.snoopy.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.snoopy.entity.vo.CourseInfoVo;
import com.bilibili.snoopy.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author LJF
 * @since 2020-05-28
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishInfo(String courseId);

    void removeCourse(String courseId);
}
