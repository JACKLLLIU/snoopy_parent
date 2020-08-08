package com.bilibili.snoopy.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.snoopy.entity.EduCourse;
import com.bilibili.snoopy.entity.vo.CourseInfoVo;
import com.bilibili.snoopy.entity.vo.CoursePublishVo;
import com.bilibili.snoopy.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author LJF
 * @since 2020-05-28
 */
@CrossOrigin //跨域
@RestController
@RequestMapping("/snoopy/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;


    //课程列表 基本实现
    //TODO  完善条件查询带分页
    @GetMapping
    public ResultMessage getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return ResultMessage.ok().data("list",list);
    }

    //删除course
    @DeleteMapping("courseId/{courseId}")
    public ResultMessage deleteCourse(@PathVariable String courseId){
        eduCourseService.removeCourse(courseId);
        return ResultMessage.ok();
    }

    @PostMapping("addCourse")
    public ResultMessage addCourse(@RequestBody CourseInfoVo courseInfoVo){
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return ResultMessage.ok().data("courseId",id);
    }

    //根据课程id查询课程信息
    @GetMapping("getCourseInfo/{courseId}")
    public ResultMessage getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfo = eduCourseService.getCourseInfo(courseId);
        return ResultMessage.ok().data("courseInfo",courseInfo);
    }

    //点击上一步后修改课程信息
    @PostMapping("updateCourseInfo")
    public ResultMessage updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return ResultMessage.ok();
    }

    @GetMapping("getPublishCourseInfo/{courseId}")
    public ResultMessage getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishInfo = eduCourseService.getCoursePublishInfo(courseId);
        return ResultMessage.ok().data("coursePublishInfo",coursePublishInfo);
    }

    @PostMapping("updateCourseStatus/{courseId}")
    public ResultMessage updateCourseStatus(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(EduCourse.course_Normal);
        eduCourseService.updateById(eduCourse);
        return ResultMessage.ok();
    }
}

