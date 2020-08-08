package com.bilibili.vod.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.snoopy.exceptionhandler.MyException;
import com.bilibili.vod.service.VodService;
import com.bilibili.vod.service.imp.VodServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("uploadAliyVideo")
    public ResultMessage uploadAlyiVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return ResultMessage.ok().data("videoId",videoId);
    }

    @DeleteMapping("removeAliyVedio/{VedioId}")
    public ResultMessage removeAlyiVedio(@PathVariable String VedioId){
        vodService.removeAlyiVedio(VedioId);
        return ResultMessage.ok();
    }

    @DeleteMapping("deleteBatch")
    public ResultMessage deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.deleteBatchVideo(videoIdList);
        return ResultMessage.ok();
    }
}
