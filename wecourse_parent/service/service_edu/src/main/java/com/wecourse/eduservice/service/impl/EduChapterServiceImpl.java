package com.wecourse.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wecourse.eduservice.client.VodClient;
import com.wecourse.eduservice.pojo.EduChapter;
import com.wecourse.eduservice.mapper.EduChapterMapper;
import com.wecourse.eduservice.pojo.EduVideo;
import com.wecourse.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wecourse.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程章节
 * @author tcp
 * @since 2021-01-28
 */
@Service
@Transactional
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    @Override
    public List<Map> getChapterByCourseId(String courseId) {
        //1.根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("course_id",courseId);
        List<EduChapter> chapterList = baseMapper.selectList(wrapperOne);//得到所有的章节

        //2.根据章节id去查询每一章中有哪些小节，然后追加成树状结构
        List<Map> chapterTree=new ArrayList<>();
        for (EduChapter chapter : chapterList) {
            QueryWrapper<EduVideo> wrapperTwo=new QueryWrapper<>();
            wrapperTwo.eq("course_id",courseId);
            wrapperTwo.eq("chapter_id",chapter.getId());
            List<EduVideo> videoList = videoService.list(wrapperTwo);//得到每一章的小节列表

            //追加成树状：每一个章节下面有许多个小节
            Map map=new HashMap();
            map.put("id",chapter.getId()); //章节ID
            map.put("title",chapter.getTitle());//章节名称

            List<Map> childrenMapList=new ArrayList<>();
            for (EduVideo video : videoList) {
                Map childrenMap=new HashMap();
                childrenMap.put("id",video.getId());
                childrenMap.put("title",video.getTitle());
                childrenMap.put("videoSourceId",video.getVideoSourceId());
                childrenMapList.add(childrenMap);
            }
            map.put("children",childrenMapList);//章节下面的所有小节内容
            chapterTree.add(map);

        }

        return chapterTree;
    }

    //根据章节id删除章节以及该章节下的所有小节内容
    @Override
    public void deleteChapter(String chapterId) {
        //删除小节下的视频
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        List<EduVideo> eduVideoList = videoService.list(wrapper);

        List<String> videoIds=new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }
        if(videoIds.size()>0) {
            vodClient.removeVideoAliyunList(videoIds);
        }

        //删除小节内容
        videoService.remove(wrapper);

        //删除章节内容
        baseMapper.deleteById(chapterId);
    }

    //根据课程id删除对应的章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
