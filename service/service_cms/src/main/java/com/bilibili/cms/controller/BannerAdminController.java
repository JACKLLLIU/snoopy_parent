package com.bilibili.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bilibili.cms.entity.CrmBanner;
import com.bilibili.cms.service.CrmBannerService;
import com.bilibili.commonUtils.ResultMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author LJF
 * @since 2020-07-31
 * 管理员接口
 */
@CrossOrigin
@RestController
@RequestMapping("/educms/bannerAdmin")
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;


    @GetMapping("pageBanner/{currentPage}/{limit}")
    public ResultMessage pageBanner(@PathVariable long currentPage,
                                    @PathVariable long limit){
        Page<CrmBanner> bannerPage = new Page<>(currentPage,limit);
        //不带条件的查询
        crmBannerService.page(bannerPage, null);
        return ResultMessage.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public ResultMessage get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return ResultMessage.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("save")
    public ResultMessage addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return ResultMessage.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public ResultMessage updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return ResultMessage.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public ResultMessage remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return ResultMessage.ok();
    }
}

