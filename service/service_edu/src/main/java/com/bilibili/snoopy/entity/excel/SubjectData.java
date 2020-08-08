package com.bilibili.snoopy.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

//因为excel储存的数据就是分类的数据，本身就是2个等级，
// 我们现在做的就是2级分类的一个操作方式
@Data
public class SubjectData {

    @ExcelProperty(index = 0)
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
