package com.wecourse.eduservice.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

//封装课程发布信息的实体类
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; //课程id
    private String title; //课程名
    private BigDecimal price;//课程价格
    private String cover; //课程封面
    private Integer lessonNum; //课时数
    private String teacherName; //讲师名
    private String subjectLevelOne; //一级分类
    private String subjectLevelTwo; //二级分类
}
