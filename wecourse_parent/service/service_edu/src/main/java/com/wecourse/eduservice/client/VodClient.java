package com.wecourse.eduservice.client;

import com.wecourse.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)//1.指定微服务的名称，表明要调用哪个微服务  2.指定熔断器
public interface VodClient {
    //要调用哪个微服务的方法，在feign接口中就参照那个方法去写就可以了
    @DeleteMapping("/eduvod/video/removeVideoAliyun/{id}")
    public Result removeVideoAliyun(@PathVariable("id") String id);//删除阿里云中的视频


    @DeleteMapping("/eduvod/video//removeVideoAliyunList")
    public Result removeVideoAliyunList(List<String> ids);//删除多个阿里云中的视频
}
