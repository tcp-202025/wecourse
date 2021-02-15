package com.wecourse.eduservice.service;

import com.wecourse.eduservice.pojo.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 课程章节
 * @author tcp
 * @since 2021-01-28
 */
public interface EduChapterService extends IService<EduChapter> {

    //返回一个课程的所有章节列表
    public List<Map> getChapterByCourseId(String courseId);

    //根据章节id删除章节以及该章节下的所有小节内容
    public void deleteChapter(String chapterId);

    //根绝courseId删除对应的章节
    public void removeChapterByCourseId(String courseId);
}
