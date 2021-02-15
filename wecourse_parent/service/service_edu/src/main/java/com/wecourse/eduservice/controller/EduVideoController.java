package com.wecourse.eduservice.controller;

import com.wecourse.commonutils.Result;
import com.wecourse.eduservice.client.VodClient;
import com.wecourse.eduservice.pojo.EduVideo;
import com.wecourse.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

/**
 * 课程视频
 * @author tcp
 * @since 2021-01-28
 */
@Api("课程管理") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return Result.ok();
    }

    //根据id查询小节的信息
    @GetMapping("/getVideoInfo/{id}")
    public Result getVideoInfo(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        return Result.ok().data("video",video);
    }

    //修改小节
    @PutMapping("/updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return Result.ok();
    }

    //删除小节(删除小节的同时删除小节中的视频
    @DeleteMapping("/deleteVideo/{id}")
    public Result deleteVideo(@PathVariable String id){
        //首先需要删除小节中的视频
        EduVideo eduVideo=videoService.getById(id);
        String videoSourceId=eduVideo.getVideoSourceId();

        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeVideoAliyun(videoSourceId);
        }

        //然后在从数据库中删除小节内容
        videoService.removeById(id);
        return Result.ok();
    }


}

