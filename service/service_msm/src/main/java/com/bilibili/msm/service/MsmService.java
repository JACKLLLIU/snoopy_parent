package com.bilibili.msm.service;

import java.util.Map;

public interface MsmService {
    boolean sendMsg(Map<String, Object> param,String phone);
}
