package com.bilibili.vod.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideoAly(MultipartFile file);

    void removeAlyiVedio(String vedioId);

    void deleteBatchVideo(List<String> videoIdList);
}
