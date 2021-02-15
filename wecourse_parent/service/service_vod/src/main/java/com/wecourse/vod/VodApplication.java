package com.wecourse.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  //oss中不需要使用到数据库，所以把数据库的相关配置排除掉
@ComponentScan(basePackages = {"com.wecourse"})
@EnableDiscoveryClient //nacos客户端（启动之后会向nacos中注册）
public class VodApplication {

    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
