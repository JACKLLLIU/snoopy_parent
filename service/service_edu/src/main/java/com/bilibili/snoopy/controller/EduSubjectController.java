package com.bilibili.snoopy.controller;


import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.snoopy.entity.subject.OneSubject;
import com.bilibili.snoopy.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author LJF
 * @since 2020-05-27
 */
@RestController
@RequestMapping("/snoopy/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    @PostMapping("addSubject")
    public ResultMessage addSubject(MultipartFile file){
        eduSubjectService.saveSubject(file,eduSubjectService);
        return ResultMessage.ok();
    }

    @GetMapping("getAllSubject")
    public ResultMessage getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllSubject();
        return ResultMessage.ok().data("data",list);
    }
}

