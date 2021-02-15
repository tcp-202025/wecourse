package com.wecourse.eduservice.client;

import com.wecourse.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

//定义一个熔断器：当VodClient远程调用失败时就会执行该熔断器
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public Result removeVideoAliyun(String id) {
        return Result.error().message("time out");
    }

    @Override
    public Result removeVideoAliyunList(List<String> ids) {
        return Result.error().message("time out");
    }
}
