package com.bilibili.snoopy.client;

import com.bilibili.commonUtils.ResultMessage;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 当服务器宕机，或者访问超时的时候，触发熔断机制，就会执行实现类
 */
@Component
public class VodClientImp implements VodClient {

    @Override
    public ResultMessage removeAlyiVedio(String VedioId) {
        return ResultMessage.error().message("删除视频出错！");
    }

    @Override
    public ResultMessage deleteBatch(List<String> videoIdList) {
        return ResultMessage.error().message("删除多个视频出错！");
    }
}
