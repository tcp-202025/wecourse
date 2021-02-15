package com.wecourse.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wecourse.commonutils.Result;
import com.wecourse.vod.service.VodService;
import com.wecourse.vod.utils.ConstantVodUtils;
import com.wecourse.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadVideoAliyun")
    public Result uploadVideoAliyun(MultipartFile file){  //用MultipartFile获取到上传的文件
        String videoId = vodService.uploadVideoAliyun(file);
        return Result.ok().data("videoId",videoId);//返回上传的视频的id值
    }

    //删除阿里云中的视频
    @DeleteMapping("/removeVideoAliyun/{id}")
    public Result removeVideoAliyun(@PathVariable String id){
        List<String> ids=new ArrayList<>();
        ids.add(id);
        vodService.removeVideoAliyun(ids);
        return Result.ok();
    }

    //删除多个阿里云中的视频
    @DeleteMapping("/removeVideoAliyunList")
    public Result removeVideoAliyunList(List<String> ids){
        vodService.removeVideoAliyun(ids);
        return Result.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        String playAuth = vodService.getPlayAuth(id);
        return Result.ok().data("playAuth",playAuth);
    }
}
