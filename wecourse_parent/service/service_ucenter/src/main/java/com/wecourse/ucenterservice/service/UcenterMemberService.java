package com.wecourse.ucenterservice.service;

import com.wecourse.ucenterservice.pojo.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wecourse.ucenterservice.pojo.vo.RegisterVo;

/**
 * @author tcp
 * @since 2021-02-09
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //用户登录
    public String login(UcenterMember member);

    //用户注册
    public void register(RegisterVo registerVo);

    //根据openid查询用户信息
    public UcenterMember getOpenIdMember(String openid);
}
