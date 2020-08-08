package com.bilibili.snoopy.controller;

import com.bilibili.commonUtils.ResultMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin //跨域
@RequestMapping("/eduservice/user")
public class EduLoginController {

    //login
    @PostMapping("login")
    public ResultMessage login(){

        return ResultMessage.ok().data("token","admin");
    }


    //getinfo
    @GetMapping("info")
    public ResultMessage getInfo(){

        return ResultMessage.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
