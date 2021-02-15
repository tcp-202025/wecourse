package com.wecourse.eduservice;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.wecourse.eduservice.mapper") //扫描mapper类
@ComponentScan(basePackages = {"com.wecourse"}) //扫描不同项目中的com.wecourse这个包（用于加载common项目中的Swagger配置文件）
@EnableDiscoveryClient //nacos客户端（启动之后会向nacos中注册）
@EnableFeignClients //启用feign组件
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class);
    }

    /**
     * 逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
