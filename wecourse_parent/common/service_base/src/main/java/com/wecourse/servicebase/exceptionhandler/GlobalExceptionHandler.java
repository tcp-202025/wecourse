package com.wecourse.servicebase.exceptionhandler;


import com.wecourse.commonutils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//全局异常处理类
@ControllerAdvice
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理..");
    }
}
