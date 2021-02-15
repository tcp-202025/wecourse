package com.wecourse.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wecourse.commonutils.Result;
import com.wecourse.eduservice.pojo.EduCourse;
import com.wecourse.eduservice.pojo.EduTeacher;
import com.wecourse.eduservice.service.EduCourseService;
import com.wecourse.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("前端的首页接口") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程，查询前4条名师记录（用于前端页面展示）
    @GetMapping("/index")
    public Result index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> courseWrapper=new QueryWrapper<>();
        courseWrapper.orderByDesc("view_count");//按照访问量来进行排序
        courseWrapper.last("limit 8");//在sql语句末尾拼接字符串
        List<EduCourse> courseList = courseService.list(courseWrapper);

        //查询前4条名师记录
        QueryWrapper<EduTeacher> teacherWrapper=new QueryWrapper<>();
        teacherWrapper.orderByDesc("sort");//按照sort字段来进行排序
        teacherWrapper.last("limit 4");//在sql语句末尾拼接字符串
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);

        return Result.ok().data("hotCourseList",courseList).data("teacherList",teacherList);
    }
}
