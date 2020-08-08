package com.bilibili.snoopy.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.snoopy.entity.EduChapter;
import com.bilibili.snoopy.entity.chapter.ChapterVo;
import com.bilibili.snoopy.service.EduChapterService;
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
@CrossOrigin
@RestController
@RequestMapping("/snoopy/eduChapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //查询课程大纲列表，根据课程id查询
    @GetMapping("getChapterVideo/{courseId}")
    public ResultMessage getchapterVideo(@PathVariable String courseId){
        List<ChapterVo> list =  eduChapterService.getchapterVideoByCourseId(courseId);
        return ResultMessage.ok().data("list",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public ResultMessage addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return ResultMessage.ok();
    }

    //根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public ResultMessage getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return ResultMessage.ok().data("chapter",eduChapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public ResultMessage updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return ResultMessage.ok();
    }

    //删除的方法
    @DeleteMapping("{chapterId}")
    public ResultMessage deleteChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag) {
            return ResultMessage.ok();
        } else {
            return ResultMessage.error();
        }

    }



}

