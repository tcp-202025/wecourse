package com.wecourse.smsservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.wecourse.smsservice.service.SmsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    private  DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G6yzVPXb1Bkho7sSyAZ", "2OLykMpX7QyrpYSprXudgnPQKPjrFz");


    //发送验证码
    @Override
    public boolean send(String phone,String checkCode){
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers",phone); //手机号
        request.putQueryParameter("SignName", "cquptBar"); //申请的阿里云模板名称
        request.putQueryParameter("TemplateCode", "SMS_190266056"); //申请阿里云 模板code
        request.putQueryParameter("TemplateParam", "{\"checkCode\":\""+checkCode+"\"}"); //验证码数据
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
