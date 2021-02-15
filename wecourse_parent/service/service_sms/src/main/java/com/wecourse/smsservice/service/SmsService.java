package com.wecourse.smsservice.service;

import java.util.Map;

public interface SmsService {
    //发送短信的方法
    public boolean send(String phone,String checkCode);
}
