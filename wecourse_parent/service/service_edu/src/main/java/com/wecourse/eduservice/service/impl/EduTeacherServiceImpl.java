package com.wecourse.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.eduservice.pojo.EduTeacher;
import com.wecourse.eduservice.mapper.EduTeacherMapper;
import com.wecourse.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 讲师列表
 * @author tcp
 * @since 2021-01-20
 */
@Service
@Transactional
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //分页条件查询讲师列表
    @Override
    public void pageTeacherByConditions(Page<EduTeacher> pageParam, Map searchMap) {
        //构造条件
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");//按sort字段进行排序

        if(searchMap==null){
            baseMapper.selectPage(pageParam,queryWrapper);//因为继承了ServiceImpl，所以可以直接用baseMapper来操作数据库
            return;
        }

        Object name= searchMap.get("name");
        Object level=searchMap.get("level");
        Object begin=searchMap.get("begin");
        Object end= searchMap.get("end");
        //根据name字段、level字段、gmt_create字段去数据库中进行条件查询
        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }

        baseMapper.selectPage(pageParam,queryWrapper);

    }

    //前台页面展示时需要调用的分页查询
    @Override
    public Map<String, Object> getFrontTeacherList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        baseMapper.selectPage(pageTeacher,wrapper);

        Map<String,Object> map=new HashMap<>();
        map.put("items",pageTeacher.getRecords());//每页的内容
        map.put("current",pageTeacher.getCurrent());//当前页
        map.put("size",pageTeacher.getSize());//每页条数
        map.put("pages",pageTeacher.getPages());//总页数
        map.put("total",pageTeacher.getTotal());//总条数
        map.put("hasNext",pageTeacher.hasNext());//是否还有下一页
        map.put("hasPrevious",pageTeacher.hasPrevious());//是否还有上一页
        return map;
    }
}
