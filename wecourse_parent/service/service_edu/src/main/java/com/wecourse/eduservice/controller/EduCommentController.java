package com.wecourse.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.commonutils.JwtUtils;
import com.wecourse.commonutils.Result;
import com.wecourse.eduservice.client.UcenterClient;
import com.wecourse.eduservice.pojo.EduComment;
import com.wecourse.eduservice.pojo.EduCourse;
import com.wecourse.eduservice.service.EduCommentService;
import com.wecourse.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 课程评论
 * @author tcp
 * @since 2021-02-14
 */
@Api("课程评论") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private UcenterClient client;

    //根据课程id查询评论列表
    @GetMapping("/pageListComment/{courseId}/{page}/{limit}")
    public Result pageListComment(@PathVariable String courseId,@PathVariable long page,@PathVariable long limit){
        Page<EduComment> pageParam = new Page<>(page, limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(pageParam,wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("items", pageParam.getRecords());
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return Result.ok().data(map);
    }

    //添加评论
    @PostMapping("/addComment")
    public Result addComment(@RequestBody EduComment eduComment, HttpServletRequest request){ //HttpServletRequest对象会在游览器访问的时候传递过来
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return Result.error().code(20001).message("请先登录");
        }

        //1.查询出用户信息以及评论信息进行保存
        Map<String,Object> map = client.getUserById(memberId);
        eduComment.setMemberId(memberId);
        eduComment.setNickname((String) map.get("nickname"));
        eduComment.setAvatar((String) map.get("avatar"));
        commentService.save(eduComment);

        //2.添加完评论之后更新课程的评论数
        String courseId = eduComment.getCourseId();
        EduCourse course = courseService.getById(courseId);
        course.setCommentCount(course.getCommentCount()+1);
        courseService.updateById(course);

        return Result.ok();
    }
}

