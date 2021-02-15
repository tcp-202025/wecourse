package com.wecourse.eduservice.service.impl;

import com.wecourse.eduservice.pojo.EduCourseDescription;
import com.wecourse.eduservice.mapper.EduCourseDescriptionMapper;
import com.wecourse.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 课程描述
 * @author tcp
 * @since 2021-01-28
 */
@Service
@Transactional
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

}
