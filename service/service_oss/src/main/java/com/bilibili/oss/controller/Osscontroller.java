package com.bilibili.oss.controller;

import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
public class Osscontroller {

    @Autowired
    private OssService ossService;

    //上传头像
    @PostMapping
    public ResultMessage uploadOssFile(MultipartFile file){
        //上传文件后，获取上传文件的路径
        String url = ossService.uploadFileAvatar(file);
        if(url==null){

        }
        return ResultMessage.ok().data("url",url);
    }

}
