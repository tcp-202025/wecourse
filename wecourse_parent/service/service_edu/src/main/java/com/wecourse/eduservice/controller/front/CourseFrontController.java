package com.wecourse.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.commonutils.Result;
import com.wecourse.eduservice.pojo.EduCourse;
import com.wecourse.eduservice.pojo.vo.CourseWebVo;
import com.wecourse.eduservice.service.EduChapterService;
import com.wecourse.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("课程的前端页面") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    //分页条件查询
    @PostMapping("/getFrontCourseList/{current}/{limit}")
    public Result getFrontCourseList(@PathVariable long current, @PathVariable long limit,@RequestBody Map searchMap){
        //创建Page对象
        Page<EduCourse> pageCourse=new Page<>(current,limit);

        Map<String,Object> map = courseService.getFrontCourseList(pageCourse,searchMap);
        //返回条件分页的所有数据
        return Result.ok().data(map);
    }

    //查询课程详情
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId){
        //1.根据课程id查询课程信息
        CourseWebVo courseWebVo=courseService.getBaseCourseInfo(courseId);

        //2.根据课程id查询章节和小节信息
        List<Map> chapterVideoList = chapterService.getChapterByCourseId(courseId);

        //3.每访问一次，就需要将该课程的访问量+1
        EduCourse course = courseService.getById(courseId);
        course.setViewCount(course.getViewCount()+1);
        courseService.updateById(course);

        return Result.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);

    }
}
