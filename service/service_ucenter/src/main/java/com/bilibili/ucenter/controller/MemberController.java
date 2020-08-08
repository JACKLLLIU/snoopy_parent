package com.bilibili.ucenter.controller;


import com.bilibili.commonUtils.JwtUtils;
import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.ucenter.entity.Member;
import com.bilibili.ucenter.entity.vo.LoginVo;
import com.bilibili.ucenter.entity.vo.RegisterVo;
import com.bilibili.ucenter.service.MemberService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author LJF
 * @since 2020-08-04
 */
@CrossOrigin
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public ResultMessage login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        //当登录成功后就需要返回一个使用jwt生成的token
        return ResultMessage.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public ResultMessage register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return ResultMessage.ok();
    }

    @ApiOperation(value = "前端拿到token进行查询用户数据")
    @GetMapping("getMemberInfo")
    public ResultMessage getMemberInfo(HttpServletRequest request){
        //token数据存储在request中,使用jwt获取id
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberIdByJwtToken);
        return ResultMessage.ok().data("userinfo",member);
    }


}
