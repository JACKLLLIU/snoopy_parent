package com.bilibili.msm.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.bilibili.msm.service.MsmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImp implements MsmService {

    @Override
    public boolean sendMsg(Map<String, Object> param,String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4G1eCKnT6hyeB5MzNP67", "dmBwcxvJfkxlh3IpIFmcX1K6sxp1KC");
        IAcsClient client = new DefaultAcsClient(profile);

        //固定参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "快乐学习在线教育网站");
        request.putQueryParameter("TemplateCode", "SMS_198916114");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//随机生成的验证码

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
