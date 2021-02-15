package com.wecourse.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.eduservice.pojo.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wecourse.eduservice.pojo.vo.CourseInfoVo;
import com.wecourse.eduservice.pojo.vo.CoursePublishVo;
import com.wecourse.eduservice.pojo.vo.CourseWebVo;

import java.util.Map;

/**
 * 课程的基本信息
 * @author tcp
 * @since 2021-01-28
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程信息
    public String saveCourse(CourseInfoVo courseInfoVo);

    //根据课程id查询课程信息
    public CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    public void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询将要发布的课程的信息
    public CoursePublishVo getPublishCourseInfo(String courseId);

    //分页条件查询
    public void pageCourseByConditions(Page<EduCourse> pageCourse, Map searchMap);

    //删除课程
    public void removeCourse(String courseId);

    //前端页面展示时需要调用的条件查询方法
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse, Map searchMap);

    //前端页面展示时需要调用的查询课程详情的方法
    public CourseWebVo getBaseCourseInfo(String courseId);
}
