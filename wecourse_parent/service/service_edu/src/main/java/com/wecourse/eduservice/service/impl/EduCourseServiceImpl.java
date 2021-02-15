package com.wecourse.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.eduservice.pojo.EduCourse;
import com.wecourse.eduservice.mapper.EduCourseMapper;
import com.wecourse.eduservice.pojo.EduCourseDescription;
import com.wecourse.eduservice.pojo.vo.CourseInfoVo;
import com.wecourse.eduservice.pojo.vo.CoursePublishVo;
import com.wecourse.eduservice.pojo.vo.CourseWebVo;
import com.wecourse.eduservice.service.EduChapterService;
import com.wecourse.eduservice.service.EduCourseDescriptionService;
import com.wecourse.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wecourse.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程的基本信息
 * @author tcp
 * @since 2021-01-28
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    //添加课程信息
    @Override
    public String saveCourse(CourseInfoVo courseInfoVo) {
        //1.向课程表中添加课程基本信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);//将courseInfoVo中的内容转换到eduCourse中去
        int insert = baseMapper.insert(eduCourse);
        if(insert<=0){
            throw new RuntimeException("添加课程信息失败");
        }

        //2.向课程简介表中添加课程简介
        EduCourseDescription courseDescription=new EduCourseDescription();
        courseDescription.setId(eduCourse.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(courseDescription);

        return eduCourse.getId();
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfo=new CourseInfoVo();

        //先从课程表中查询内容
        EduCourse course=baseMapper.selectById(courseId);

        //再从课程描述表中查出内容
        EduCourseDescription courseDescription=courseDescriptionService.getById(courseId);

        //最后合并到CourseInfoVo中
        BeanUtils.copyProperties(course,courseInfo);
        BeanUtils.copyProperties(courseDescription,courseInfo);

        return courseInfo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //首先修改课程表
        EduCourse course=new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);
        baseMapper.updateById(course);

        //然后修改课程描述表
        EduCourseDescription courseDescription=new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        courseDescriptionService.updateById(courseDescription);
    }

    //根据课程id查询将要发布的课程的信息
    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    //分页条件查询
    @Override
    public void pageCourseByConditions(Page<EduCourse> pageCourse, Map searchMap) {
        //构造条件
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");

        if(searchMap==null){
            baseMapper.selectPage(pageCourse,wrapper);
        }

        Object title=searchMap.get("title");//课程标题
        Object status=searchMap.get("status");//课程状态
        String isPublish="";
        if(status!=null) {
            if ("1".equals(status.toString())) {
                isPublish = "Normal"; //前端传过来的值中，1表示已发布
            } else if ("0".equals(status.toString())) {
                isPublish = "Draft"; //0表示未发布
            }
        }

        Object beginPrice=searchMap.get("beginPrice");//课程价格区间
        Object endPrice= searchMap.get("endPrice");//课程价格区间

        //根据name字段、level字段、price字段去数据库中进行条件查询
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status", isPublish);
        }
        if(!StringUtils.isEmpty(beginPrice)){
            wrapper.ge("price",beginPrice);
        }
        if(!StringUtils.isEmpty(endPrice)){
            wrapper.le("price",endPrice);
        }

        baseMapper.selectPage(pageCourse,wrapper);
    }


    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);

        //根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //根据课程id删除描述信息
        courseDescriptionService.removeById(courseId);

        //删除课程本身
        baseMapper.deleteById(courseId);
    }


    //前端页面展示时需要调用的条件查询方法：用户点击不同条件后显示不同的课程
    @Override
    public Map<String, Object> getFrontCourseList(Page<EduCourse> pageCourse, Map searchMap) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(searchMap.get("subjectParentId"))) { //一级分类
            wrapper.eq("subject_parent_id",searchMap.get("subjectParentId"));
        }
        if(!StringUtils.isEmpty(searchMap.get("subjectId"))) { //二级分类
            wrapper.eq("subject_id",searchMap.get("subjectId"));
        }
        if(!StringUtils.isEmpty(searchMap.get("buyCountSort"))) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(searchMap.get("gmtCreateSort"))) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(searchMap.get("priceSort"))) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse,wrapper);

        Map<String,Object> map=new HashMap<>();
        map.put("items",pageCourse.getRecords());//每页的内容
        map.put("current",pageCourse.getCurrent());//当前页
        map.put("size",pageCourse.getSize());//每页条数
        map.put("pages",pageCourse.getPages());//总页数
        map.put("total",pageCourse.getTotal());//总条数
        map.put("hasNext",pageCourse.hasNext());//是否还有下一页
        map.put("hasPrevious",pageCourse.hasPrevious());//是否还有上一页
        return map;
    }

    //前端页面展示时需要调用的查询课程详情的方法
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
