package com.wecourse.eduservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.commonutils.Result;
import com.wecourse.eduservice.pojo.EduTeacher;
import com.wecourse.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 讲师列表
 * @author tcp
 * @since 2021-01-20
 */
@Api("讲师管理") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //根据id查询
    @GetMapping("/findOneTeacher/{id}")
    public Result findOneTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return Result.ok().data("teacher",eduTeacher);
    }

    //查询所有
    @GetMapping("/findAllTeacher")
    public Result findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);//wrapper是传入的条件
        return Result.ok().data("items",list); //链式编程返回
    }

    //分页查询
    @GetMapping("/pageListTeacher/{current}/{limit}")
    public Result pageListTeacher(@PathVariable long current,@PathVariable long limit){
        //创建Page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        teacherService.page(pageTeacher, null);
        //返回总条数和内容集合
        return Result.ok().data("total",pageTeacher.getTotal()).data("rows",pageTeacher.getRecords());
    }

    //分页条件查询
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public Result pageTeacherCondition(@PathVariable long current, @PathVariable long limit,@RequestBody Map searchMap){
        //创建Page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        //调用方法实现分页查询
        teacherService.pageTeacherByConditions(pageTeacher,searchMap);
        return Result.ok().data("total",pageTeacher.getTotal()).data("rows",pageTeacher.getRecords());
    }

    //增加讲师
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.save(eduTeacher);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    //修改讲师
    @PutMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);//会根据传入的内容找出修改的值在更改，没传入的值不会更改
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    //删除讲师
    @DeleteMapping("/deleteTeacher/{id}")
    public Result deleteTeacher(@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

