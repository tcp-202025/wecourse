package com.wecourse.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //上传文件到oss
    public String uploadFile(MultipartFile file);
}
