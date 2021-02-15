package com.wecourse.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.eduservice.pojo.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 讲师列表
 * @author tcp
 * @since 2021-01-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //分页条件查询讲师列表
    public void pageTeacherByConditions(Page<EduTeacher> pageParam, Map searchMap);

    //前台页面展示时需要调用的分页查询
    public Map<String, Object> getFrontTeacherList(Page<EduTeacher> pageTeacher);
}
