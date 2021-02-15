package com.wecourse.eduservice.excel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

//excel表的对应实体类
@Data
public class SubjectData {

    @ExcelProperty(index = 0) //第一列：一级分类
    private String oneSubjectName;

    @ExcelProperty(index = 1) //第二列：二级分类
    private String twoSubjectName;

}
