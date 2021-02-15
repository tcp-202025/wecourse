package com.wecourse.eduservice.service;

import com.wecourse.eduservice.pojo.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 课程视频
 * @author tcp
 * @since 2021-01-28
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程id删除小节
    public void removeVideoByCourseId(String courseId);
}
