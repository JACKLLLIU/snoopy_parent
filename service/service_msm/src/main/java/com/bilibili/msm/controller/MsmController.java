package com.bilibili.msm.controller;

import com.bilibili.commonUtils.ResultMessage;
import com.bilibili.msm.service.MsmService;
import com.bilibili.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    //实现5分钟失效
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public ResultMessage sendMsg(@PathVariable String phone){
        //首先在redis中获取
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return ResultMessage.ok();
        }

        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean b = msmService.sendMsg(param,phone);
        if(b){
            //如果发送成功就，用redis存储起来
            redisTemplate.opsForValue().set(phone,code,50, TimeUnit.MINUTES);
            return ResultMessage.ok();
        }else {
            return ResultMessage.error().message("发送出错请与管理员联系");
        }
    }

}
