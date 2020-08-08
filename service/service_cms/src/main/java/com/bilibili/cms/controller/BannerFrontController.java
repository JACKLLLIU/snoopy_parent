package com.bilibili.cms.controller;


import com.bilibili.cms.entity.CrmBanner;
import com.bilibili.cms.service.CrmBannerService;
import com.bilibili.commonUtils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService crmBannerService;

    @Cacheable(key = "'getBannerList'",value = "banner")
    @GetMapping("getAllBanner")
    public ResultMessage getAllBanner(){
        List<CrmBanner> bannerList =  crmBannerService.selectAll();
        return ResultMessage.ok().data("bannerList",bannerList);
    }

}
