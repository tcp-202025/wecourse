package com.wecourse.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wecourse.eduservice.client.VodClient;
import com.wecourse.eduservice.pojo.EduVideo;
import com.wecourse.eduservice.mapper.EduVideoMapper;
import com.wecourse.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程视频
 * @author tcp
 * @since 2021-01-28
 */
@Service
@Transactional
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //根据课程id删除小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        //1.首先需要将课程下的视频id删除
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);

        List<String> videoIds=new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }

        if(videoIds.size()>0) {
            vodClient.removeVideoAliyunList(videoIds);
        }


        //2.将视频删除后然后再将小节删除
        baseMapper.delete(wrapper);
    }
}
