package com.wecourse.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wecourse.vod.utils.ConstantVodUtils;
import com.wecourse.vod.service.VodService;
import com.wecourse.vod.utils.InitObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {

    //上传视频到阿里云
    @Override
    public String uploadVideoAliyun(MultipartFile file) {
        try {
            //fileName：上传文件原始名称，比如asdjksafflk.mp4
            String fileName = file.getOriginalFilename();

            //title：上传之后在OSS中的显示名称：asdjksafflk
            String title = fileName.substring(0, fileName.lastIndexOf("."));

            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);//获取阿里云的UploadStreamRequest对象

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request); //调用uploadStream()方法流式上传，并且返回上传结果

            String videoId = "";
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除阿里云中的视频
    @Override
    public void removeVideoAliyun(List<String> ids) {
        try {
            //1.初始化阿里云视频点播的操作对象
            DefaultAcsClient defaultAcsClient = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            //2.创建删除视频的request对象
            DeleteVideoRequest request=new DeleteVideoRequest();

            String videoIds= StringUtils.join(ids.toArray(),",");
            request.setVideoIds(videoIds);//设置要删除视频的id（多个id用,隔开:1001,1002,1003）

            //3.调用方法实现删除
            defaultAcsClient.getAcsResponse(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据视频id获取视频的播放凭证
    @Override
    public String getPlayAuth(String id) {
        try {
            //1.创建阿里云视频点播的初始化对象
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            //2.创建获取凭证的request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //3.向request中设置视频id
            request.setVideoId(id);

            //4.调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return playAuth;
        } catch (Exception e) {
            throw new RuntimeException("获取视频凭证失败");
        }
    }
}
