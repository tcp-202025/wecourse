package com.wecourse.eduservice.controller;


import com.wecourse.commonutils.Result;
import com.wecourse.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 课程分类
 * @author tcp
 * @since 2021-01-27
 */
@Api("课程管理") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传的文件，把文件内容读取出来，然后添加
    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file){ //file是上传的文件
        subjectService.saveSubject(file,subjectService);
        return Result.ok();
    }

    //查询所有的课程分类（返回树形结构）
    @GetMapping("/getAllSubject")
    public Result getAllSubject(){
        List<Map> subjectTree = subjectService.findSubjectTree();
        return Result.ok().data("list",subjectTree);
    }
}

