package com.wecourse.ucenterservice.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//创建一个实体类用于封装前端传来的注册时的数据
@Data
public class RegisterVo {
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "验证码")
    private String code;
}
