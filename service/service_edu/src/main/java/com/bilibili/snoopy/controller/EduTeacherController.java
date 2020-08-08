package com.bilibili.snoopy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.snoopy.entity.EduTeacher;
import com.bilibili.snoopy.entity.vo.TeacherQuery;
import com.bilibili.snoopy.exceptionhandler.MyException;
import com.bilibili.snoopy.service.EduTeacherService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author LJF
 * @since 2020-05-20
 */
@Api(description = "讲师管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/snoopy/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //1、查询所有讲师的数据
    @ApiOperation(value = "讲师列表")
    @GetMapping("findAll")
    public ResultMessage findAllTeacher(){
        List<EduTeacher> eduTeachers = eduTeacherService.list(null);
/*        try {
            int i = 1/0;
        }catch (Exception e){
            throw new MyException(50000,"自定义异常");
        }*/
        return ResultMessage.ok().data("eduTeachers",eduTeachers);
    }

    //2、删除DeleteMapping提交的方式流浪器是不能测试的
    //需要postman或者swagger进行测试
    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public ResultMessage removeTeacher(@PathVariable
                                             @ApiParam(name="id",value = "讲师id",required = true) String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return ResultMessage.ok();
        }
        return ResultMessage.error();
    }

    //3、不传参的讲师分页功能
    @ApiOperation(value = "不传参的讲师分页功能")
    @GetMapping("pageTeacher/{current}/{limit}")
    public ResultMessage pageTeacher(@PathVariable long current,
                                     @PathVariable long limit){
        Page<EduTeacher> page = new Page<>(current,limit);
        eduTeacherService.page(page,null);
        long total  = page.getTotal();
        List<EduTeacher> records = page.getRecords();

        //2种返回数据的方式
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total",total);
        map.put("rows",records);
        //return ResultMessage.ok().data(map);
        return ResultMessage.ok().data("total",total).data("rows",records);
    }

    //3、传参的讲师分页功能
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public ResultMessage pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                     @RequestBody TeacherQuery teacherQuery){
        Page<EduTeacher> page = new Page<>(current,limit);
        //构建查询条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>();
        //多个条件进行查询的时候，就需要判断有的条件是否为空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        wrapper.orderByDesc("gmt_create");
        eduTeacherService.page(page,wrapper);
        long total  = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return ResultMessage.ok().data("total",total).data("rows",records);
    }

    //添加讲师接口
    @PostMapping("addTeacher")
    public ResultMessage addTeacher(@RequestBody(required = false) EduTeacher eduTeacher){
        Boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return ResultMessage.ok();
        }
        return ResultMessage.error();
    }

    //修改功能有2个接口，根据id查出数据回显，再修改
    @GetMapping("getTeacher/{id}")
    public ResultMessage getTeacher(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return ResultMessage.ok().data("teacher",teacher);
    }

    @PostMapping("updateTeacher")
    public ResultMessage updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag){
            return ResultMessage.ok();
        }
        return ResultMessage.error();
    }

}

