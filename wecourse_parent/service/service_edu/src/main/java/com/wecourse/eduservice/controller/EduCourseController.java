package com.wecourse.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.commonutils.Result;
import com.wecourse.eduservice.pojo.EduCourse;
import com.wecourse.eduservice.pojo.vo.CourseInfoVo;
import com.wecourse.eduservice.pojo.vo.CoursePublishVo;
import com.wecourse.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 课程的基本信息
 * @author tcp
 * @since 2021-01-28
 */
@Api("课程管理") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //添加课程信息
    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody CourseInfoVo courseInfoVo){
        String courseId = courseService.saveCourse(courseInfoVo);//添加成功后需要返回添加的这个课程的id，以便后边使用
        return Result.ok().data("courseId",courseId);
    }

    //根据课程id查询课程基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfo",courseInfoVo);
    }

    //修改课程信息
    @PutMapping("/updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    //根据课程id查询将要发布的课程的信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public Result getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo=courseService.getPublishCourseInfo(courseId);
        return Result.ok().data("publishCourse",coursePublishVo);
    }

    //课程的最终发布（即：修改课程的状态）
    @PostMapping("publishCourse/{courseId}")
    public Result publishCourse(@PathVariable String courseId){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal"); //将课程的状态修改为Normal
        courseService.updateById(eduCourse);
        return Result.ok();
    }

    //分页条件查询
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public Result pageCourseCondition(@PathVariable long current, @PathVariable long limit,@RequestBody Map searchMap){
        //创建Page对象
        Page<EduCourse> pageCourse=new Page<>(current,limit);
        //调用方法实现分页查询
        courseService.pageCourseByConditions(pageCourse,searchMap);
        return Result.ok().data("total",pageCourse.getTotal()).data("rows",pageCourse.getRecords());
    }

    //删除课程
    @DeleteMapping("/deleteCourse/{courseId}")
    public Result deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return Result.ok();
    }
}

