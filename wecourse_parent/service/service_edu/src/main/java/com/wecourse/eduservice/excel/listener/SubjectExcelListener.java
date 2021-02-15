package com.wecourse.eduservice.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wecourse.eduservice.excel.vo.SubjectData;
import com.wecourse.eduservice.pojo.EduSubject;
import com.wecourse.eduservice.service.EduSubjectService;

//使用easyExcel读取文件内容时需要的监听器
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    /**
     * 因为SubjectExcelListener这个类不能交给spring管理，需要我们自己手动new，所以在这个类中不能通过注解来注入其它对象
     * 解决方法：通过构造函数注入
     */
    private EduSubjectService subjectService;
    public SubjectExcelListener(){};
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //一行一行读取数据（从第二行开始读，第一行默认为表头）
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            return;
        }

        //一行一行读取，每次读取有两个值，第一个值为一级分类，第二个值为二级分裂
        //后端   JAVA
        //后端   GO
        //后端   C++
        //前端   JS
        //前端   VUE

        //添加之前需要判断一级分类和二级分类名是否在数据库中已经存在
        EduSubject existOneSubject = existOneSubject(subjectData.getOneSubjectName());
        if(existOneSubject == null){
             //查询数据库为空，表示没有相同一级分类，进行添加
            existOneSubject=new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }


        if(existTwoSubject(subjectData.getTwoSubjectName(),existOneSubject.getId()) == null){
            //判断到没有相同二级分类，进行添加
            EduSubject eduSubject=new EduSubject();
            eduSubject.setParentId(existOneSubject.getId());
            eduSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(eduSubject);
        }

    }

    //读取完成后的方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("添加完成，共添加"+analysisContext.getTotalCount()+"条");
    }

    //判断一级分类是否重复:SELECT * FROM edu_subject WHERE title=? AND parent_id=0
    public EduSubject existOneSubject(String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject subject = this.subjectService.getOne(wrapper);
        return subject;
    }

    //判断二级分类是否重复:SELECT * FROM edu_subject WHERE title=? AND parent_id=?
    public EduSubject existTwoSubject(String name,String parentId){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",parentId);
        EduSubject subject = this.subjectService.getOne(wrapper);
        return subject;
    }
}
