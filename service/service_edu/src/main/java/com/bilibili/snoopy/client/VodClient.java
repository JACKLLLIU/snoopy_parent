package com.bilibili.snoopy.client;

import com.bilibili.commonUtils.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "service-vod",fallback = VodClientImp.class)
@Component
public interface VodClient {

    @DeleteMapping("/eduvod/video/removeAliyVedio/{VedioId}")
    public ResultMessage removeAlyiVedio(@PathVariable("VedioId") String VedioId);

    @DeleteMapping("/eduvod/video/deleteBatch")
    public ResultMessage deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
