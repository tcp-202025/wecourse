package com.wecourse.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自定义的异常类
@Data
@AllArgsConstructor  //生成有参数构造方法
@NoArgsConstructor   //生成无参数构造
public class WeCourseException extends RuntimeException {
    private Integer code;//状态码
    private String msg;//异常信息
}
