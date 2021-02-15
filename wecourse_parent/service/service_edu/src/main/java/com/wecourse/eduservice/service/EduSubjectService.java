package com.wecourse.eduservice.service;

import com.wecourse.eduservice.pojo.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 课程分类
 * @author tcp
 * @since 2021-01-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    //通过读取excel文件内容，批量添加课程分类
    public void saveSubject(MultipartFile file,EduSubjectService subjectService);

    //查询课程分类（返回树形结构）
    public List<Map> findSubjectTree();
}
