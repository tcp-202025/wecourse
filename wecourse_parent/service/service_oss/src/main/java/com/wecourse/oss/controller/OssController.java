package com.wecourse.oss.controller;

import com.wecourse.commonutils.Result;
import com.wecourse.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    //上传图片的方法
    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){ //MultipartFile用于获取上传文件
        //返回图片存储的路径
        String url = ossService.uploadFile(file);
        if(url==null){
            return Result.error();
        }
        return Result.ok().data("url",url);
    }
}
