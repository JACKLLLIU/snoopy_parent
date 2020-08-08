package com.bilibili.snoopy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.snoopy.entity.EduCourse;
import com.bilibili.snoopy.entity.EduTeacher;
import com.bilibili.snoopy.service.EduCourseService;
import com.bilibili.snoopy.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //首页需要展示8个热门课程，4个人气教师
    @GetMapping("index")
    public ResultMessage index(){
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(courseQueryWrapper);

        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(teacherQueryWrapper);
        return ResultMessage.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
