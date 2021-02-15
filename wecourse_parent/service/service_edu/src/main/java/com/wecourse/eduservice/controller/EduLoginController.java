package com.wecourse.eduservice.controller;

import com.wecourse.commonutils.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员登录
 * @author tcp
 * @since 2021-01-22
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/admin")
public class EduLoginController {

    //后台管理系统登录
    @PostMapping("/login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    @GetMapping("/info")
    public Result info(){
        return Result.ok().data("roles","[admin]").data("name","tcp").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
