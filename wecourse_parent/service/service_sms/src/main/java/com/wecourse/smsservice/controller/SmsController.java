package com.wecourse.smsservice.controller;

import com.wecourse.commonutils.Result;
import com.wecourse.smsservice.service.SmsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/smsservice/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信的方法
    @GetMapping("/send/{phone}")
    public Result sendMsg(@PathVariable String phone) {

        String checkCode = RandomStringUtils.randomNumeric(6);//生成6位随机数
        //调用service发送短信的方法
        boolean isSend = smsService.send(phone,checkCode);
        if(isSend) {
            //发送成功，把发送成功验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(phone,checkCode,15, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("短信发送失败");
        }
    }
}
