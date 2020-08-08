package com.bilibili.cms.service;

import com.bilibili.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.commonUtils.ResultMessage;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author LJF
 * @since 2020-07-31
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAll();
}
