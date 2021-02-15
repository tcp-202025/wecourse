package com.wecourse.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    //上传视频到阿里云
    public String uploadVideoAliyun(MultipartFile file);

    //删除阿里云中的视频
    public void removeVideoAliyun(List<String> ids);

    //根据视频id获取视频的播放凭证
    public String getPlayAuth(String id);
}
