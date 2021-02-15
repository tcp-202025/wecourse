package com.wecourse.eduservice.mapper;

import com.wecourse.eduservice.pojo.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wecourse.eduservice.pojo.vo.CoursePublishVo;
import com.wecourse.eduservice.pojo.vo.CourseWebVo;

/**
 * 课程的基本信息
 * @author tcp
 * @since 2021-01-28
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //得到将要发布的课程的信息，用于前端回显
    public CoursePublishVo getPublishCourseInfo(String courseId);

    //前端页面展示时需要调用的查询课程详情的方法
    public CourseWebVo getBaseCourseInfo(String courseId);
}
