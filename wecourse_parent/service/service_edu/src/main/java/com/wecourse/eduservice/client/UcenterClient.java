package com.wecourse.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Component
@FeignClient(name = "service-ucenter")//1.指定微服务的名称，表明要调用哪个微服务
public interface UcenterClient {

    @GetMapping("/ucenterservice/member/getUserById/{id}")
    public Map<String,Object> getUserById(@PathVariable String id);
}
