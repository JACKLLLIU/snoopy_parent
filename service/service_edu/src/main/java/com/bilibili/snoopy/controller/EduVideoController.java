package com.bilibili.snoopy.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.snoopy.client.VodClient;
import com.bilibili.snoopy.entity.EduVideo;
import com.bilibili.snoopy.exceptionhandler.MyException;
import com.bilibili.snoopy.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author LJF
 * @since 2020-05-28
 */
@CrossOrigin
@RestController
@RequestMapping("/snoopy/eduVideo")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    //注入vodclient
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public ResultMessage addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return ResultMessage.ok();
    }

    //删除小节 传入的是小节id
    // TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
    @DeleteMapping("{id}")
    public ResultMessage deleteVideo(@PathVariable String id) {
        //小节id是主键（video表） 我们还要去删除小节中阿里云中的视频
        //根据id查到videosouceid 去删除
        EduVideo video = eduVideoService.getById(id);
        String videoId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoId)){
            ResultMessage resultMessage = vodClient.removeAlyiVedio(videoId);
            if(resultMessage.getCode()==20001){
                throw new MyException(20001,"出发熔断，删除视频失败");
            }
        }
        //删除小节
        eduVideoService.removeById(id);
        return ResultMessage.ok();
    }

}

