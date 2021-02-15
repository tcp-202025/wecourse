package com.wecourse.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wecourse.eduservice.excel.listener.SubjectExcelListener;
import com.wecourse.eduservice.pojo.EduSubject;
import com.wecourse.eduservice.mapper.EduSubjectMapper;
import com.wecourse.eduservice.excel.vo.SubjectData;
import com.wecourse.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程分类
 * @author tcp
 * @since 2021-01-27
 */
@Service
@Transactional
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //通过读取excel文件内容，批量添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //获取文件输入流
            InputStream in=file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //查询课程分类，返回树形结构 TODO：后期完善：放入缓存中
    /**
     * "data": {
     *     "list": [
     *       {
     *         "id": "1178214681118568449",
     *         "title": "后端开发"
     *         "children": [
     *           {
     *             "id": "1178214681139539969",
     *             "title": "Java"
     *           },
     *           {
     *             "id": "1178585108407984130",
     *             "title": "Python"
     *           }
     *         ]
     *       },
     */
    @Override
    public List<Map> findSubjectTree() {
        //1.先查询出所有的一级分类列表
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> parentList = baseMapper.selectList(wrapperOne);

        //2.查询每一个一级分类下的二级分类，追加成树状结构
        List<Map> subjectTree=new ArrayList<>();
        for (EduSubject parentSubject :parentList) {   //遍历每一个一级分类
            Map map=new HashMap();
            map.put("id",parentSubject.getId()); //一级分类ID
            map.put("title",parentSubject.getTitle()); //一级分类名称

            List<Map> childrenMapList=new ArrayList<>();
            QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
            wrapperTwo.eq("parent_id",parentSubject.getId());
            List<EduSubject> childrenList = baseMapper.selectList(wrapperTwo);  //查询出每一个一级分类下的二级分类
            for (EduSubject childrenSubject : childrenList) {
                Map childrenMap=new HashMap();
                childrenMap.put("id",childrenSubject.getId());
                childrenMap.put("title",childrenSubject.getTitle());
                childrenMapList.add(childrenMap);
            }
            map.put("children",childrenMapList); //一级分类下的二级分类列表
            subjectTree.add(map);

        }
        return subjectTree;
    }
}
