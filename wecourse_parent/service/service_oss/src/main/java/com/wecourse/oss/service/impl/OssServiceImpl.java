package com.wecourse.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wecourse.oss.service.OssService;
import com.wecourse.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    /**
     * 上传文件到oss
     */
    @Override
    public String uploadFile(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            //1.创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //2.获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            //3.获取文件名称
            String fileName = file.getOriginalFilename();

            //4.重命名文件，生成唯一文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //kizsadresdadafa-00001.jpg
            fileName = uuid+fileName;

            //5.把文件按照日期进行分类
            //   获取当前日期:2021-01-25
            String datePath = new DateTime().toString("yyyy-MM-dd");
            //   拼接:2021-01-25/kizsadresdadafa-00001.jpg
            fileName = datePath+"/"+fileName;

            //6.调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName,fileName, inputStream);

            //7.关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //  https://tcp-oss.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
