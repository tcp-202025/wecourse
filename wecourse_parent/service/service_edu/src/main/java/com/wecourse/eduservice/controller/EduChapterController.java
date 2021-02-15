package com.wecourse.eduservice.controller;


import com.wecourse.commonutils.Result;
import com.wecourse.eduservice.pojo.EduChapter;
import com.wecourse.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 课程章节
 * @author tcp
 * @since 2021-01-28
 */
@Api("课程管理") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //返回一个课程的所有章节列表
    @GetMapping("/getChapter/{courseId}")
    public Result getChapter(@PathVariable String courseId){
        List<Map> list=chapterService.getChapterByCourseId(courseId);
        return Result.ok().data("allChapter",list);
    }

    //添加章节
    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return Result.ok();
    }

    //根据章节id查询
    @GetMapping("/getChapterInfo/{id}")
    public Result getChapterInfo(@PathVariable String id){
        EduChapter chapter = chapterService.getById(id);
        return Result.ok().data("chapter",chapter);
    }

    //修改
    @PutMapping("/updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return Result.ok();
    }

    //删除
    @DeleteMapping("/deleteChapter/{id}")
    public Result deleteChapter(@PathVariable String id){
        chapterService.deleteChapter(id);
        return Result.ok();
    }
}

