package com.bilibili.snoopy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bilibili.snoopy.entity.EduCourse;
import com.bilibili.snoopy.entity.EduCourseDescription;
import com.bilibili.snoopy.entity.vo.CourseInfoVo;
import com.bilibili.snoopy.entity.vo.CoursePublishVo;
import com.bilibili.snoopy.exceptionhandler.MyException;
import com.bilibili.snoopy.mapper.EduCourseMapper;
import com.bilibili.snoopy.service.EduChapterService;
import com.bilibili.snoopy.service.EduCourseDescriptionService;
import com.bilibili.snoopy.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.snoopy.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author LJF
 * @since 2020-05-28
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    //添加CourseInfoVo信息，需要插入2张表
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表中添加数据
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        System.out.println(eduCourse);
        int flag = baseMapper.insert(eduCourse);
        if(0==flag){
            throw new MyException(20001,"添加课程信息失败");
        }

        String cid = eduCourse.getId();
        //向课程描述表中添加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(cid);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if(!save){
            throw new MyException(20001,"添加描述信息失败");
        }
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //CourseInfoVo里的信息由课程表，和课程描述表，2个表的信息组成
        //因此需要查询2个表的信息

        //baseMapper只是适合单表操作，具体是怎么识别查那一张表。。。
        //不现在都是有一个封装类，来查询多张表拼接，成一个封装类的对象数据

        //根据id查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //id查询描述表
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }


    //修改课程
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //先去修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i==0){
            throw new MyException(20001,"课程修改失败");
        }

        //修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String courseId) {
        CoursePublishVo coursePublishVo =  baseMapper.getCoursePublishInfo(courseId);
        return coursePublishVo;
    }

    @Override
    public void removeCourse(String courseId) {
        //需要删除多张表的数据
        //课程描述表
        boolean b = eduCourseDescriptionService.removeById(courseId);

        //章节表
        eduChapterService.removeByCourseId(courseId);
        //video表

        eduVideoService.removeByCourseId(courseId);

        //course表
        int i = baseMapper.deleteById(courseId);
        if(i==0){
            throw new MyException(20001,"删除失败");
        }
    }
}
