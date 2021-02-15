package com.wecourse.ucenterservice.controller;


import com.wecourse.commonutils.JwtUtils;
import com.wecourse.commonutils.Result;
import com.wecourse.ucenterservice.pojo.UcenterMember;
import com.wecourse.ucenterservice.pojo.vo.RegisterVo;
import com.wecourse.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tcp
 * @since 2021-02-09
 */
@Api("用户中心") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/ucenterservice/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //用户登录
    @PostMapping("/login")
    public Result login(@RequestBody UcenterMember member){
        //调用方法登录，登录成功后需要返回一个token值
        String token = memberService.login(member);
        return Result.ok().data("token",token);
    }

    //用户注册
    @PostMapping("/register")
    public Result register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return Result.ok();
    }

    //根据token获取用户信息
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){  //HttpServletRequest对象代表客户端的请求，当客户端通过HTTP协议访问服务器时，HTTP请求头中的所有信息都封装在这个对象中，通过这个对象提供的方法，可以获得客户端请求的所有信息。
        //解析token，获取用户id
        String userId = JwtUtils.getMemberIdByJwtToken(request);

        //通过id查询用户信息
        UcenterMember member = memberService.getById(userId);
        return Result.ok().data("userInfo",member);
    }

    //根据用户id获取用户信息
    @GetMapping("/getUserById/{id}")
    public Map<String,Object> getUserById(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("id",member.getId());
        map.put("openid",member.getOpenid());
        map.put("mobile",member.getMobile());
        map.put("nickname",member.getNickname());
        map.put("sex",member.getSex());
        map.put("age",member.getAge());
        map.put("avatar",member.getAvatar());

        return map;
    }
}

