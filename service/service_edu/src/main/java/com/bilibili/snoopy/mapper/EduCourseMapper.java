package com.bilibili.snoopy.mapper;

import com.bilibili.snoopy.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.snoopy.entity.vo.CoursePublishVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author LJF
 * @since 2020-05-28
 */
@Repository
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getCoursePublishInfo(String courseId);
}
