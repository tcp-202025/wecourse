package com.wecourse.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.commonutils.Result;
import com.wecourse.eduservice.pojo.EduCourse;
import com.wecourse.eduservice.pojo.EduTeacher;
import com.wecourse.eduservice.service.EduCourseService;
import com.wecourse.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("讲师的前端页面") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //分页查询讲师
    @GetMapping("/getFrontTeacherList/{current}/{limit}")
    public Result getFrontTeacherList(@PathVariable long current, @PathVariable long limit){
        //创建Page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        Map<String,Object> map=teacherService.getFrontTeacherList(pageTeacher);

        //返回分页的所有数据
        return Result.ok().data(map);
    }

    //查询讲师详情
    @GetMapping("/getFrontTeacherInfo/{teacherId}")
    public Result getFrontTeacherInfo(@PathVariable String teacherId){
        //根据讲师id查询讲师基本信息
        EduTeacher teacher = teacherService.getById(teacherId);

        //根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);

        return Result.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
