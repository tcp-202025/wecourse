package com.wecourse.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wecourse.commonutils.JwtUtils;
import com.wecourse.commonutils.MD5;
import com.wecourse.ucenterservice.pojo.UcenterMember;
import com.wecourse.ucenterservice.mapper.UcenterMemberMapper;
import com.wecourse.ucenterservice.pojo.vo.RegisterVo;
import com.wecourse.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author tcp
 * @since 2021-02-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //用户登录
    @Override
    public String login(UcenterMember member) {
        //1.获取手机号和密码
        String mobile=member.getMobile();
        String password=member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            return "";
        }


        //2.判断手机号是否存在
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember selectMember = baseMapper.selectOne(wrapper);
        if(selectMember==null){
            return "";
        }

        //3.判断密码是否匹配
        if(! (MD5.encrypt(password).equals(selectMember.getPassword())) ){
            return "";
        }

        //4.判断用户状态是否被禁用
        if(selectMember.getIsDisabled()){
            return "";
        }

        //5.上述判断都通过，则登录成功，需要生成一个token
        String jwtToken = JwtUtils.getJwtToken(selectMember.getId(), selectMember.getNickname());

        return jwtToken;
    }


    //用户注册
    @Override
    public void register(RegisterVo registerVo) {
        //1.获取注册的数据
        String mobile=registerVo.getMobile();//手机号
        String code=registerVo.getCode();//验证码
        String nickName=registerVo.getNickname();//用户名
        String password=registerVo.getPassword();//密码

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickName)){
            throw new RuntimeException("注册失败");
        }

        //2.判断验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(mobile);//取出redis中的验证码
        //跟输入的验证码进行比较
        if(!code.equals(redisCode)){
            throw new RuntimeException("验证码无效");
        }

        //3.判断手机号是否已经存在
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember selectMember = baseMapper.selectOne(wrapper);
        if(selectMember!=null){
            throw new RuntimeException("该手机号已注册");
        }

        //上述校验都通过，就可以将注册数据添加到数据库中
        UcenterMember member=new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickName);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://tcp-oss.oss-cn-beijing.aliyuncs.com/2021-02-06/04cd3cfc3bd846bcb3d56d72e4ff1dfefile.png");
        baseMapper.insert(member);
    }

    //根据openid查询用户信息
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }
}
