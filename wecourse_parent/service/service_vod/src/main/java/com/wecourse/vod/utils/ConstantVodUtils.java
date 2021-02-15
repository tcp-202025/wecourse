package com.wecourse.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//常量类：读取配置文件中的数据（凡是实现InitializingBean接口的类，在初始化bean的时候都会执行该方法）
@Component
public class ConstantVodUtils implements InitializingBean {

    //读取配置文件内容（@Value：可以读取配置文件的值然后给属性注入）
    @Value("${aliyun.vod.file.keyid}")
    private String keyid;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    //定义公开静态常量
    public static String ACCESS_KEY_SECRET;
    public static String ACCESS_KEY_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
    }
}
