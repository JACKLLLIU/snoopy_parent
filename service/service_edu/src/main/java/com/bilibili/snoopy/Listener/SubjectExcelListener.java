package com.bilibili.snoopy.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bilibili.snoopy.entity.EduSubject;
import com.bilibili.snoopy.entity.excel.SubjectData;
import com.bilibili.snoopy.exceptionhandler.MyException;
import com.bilibili.snoopy.service.EduSubjectService;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //SubjectExcelListener不能交给spring去管理，所以需要有参构造，
    // eduSubjectService对象就不能使用注解的方式，传递进来。所以采用有参构造。
    private EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }

    //invoke方法是在excel表格中一行行的进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new MyException(20001,"文件数据为空");
        }
        //没进来一行数据
        //第一列为一级分类
        //判断一级分类不能重复添加
        EduSubject OneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if(OneSubject == null){
            //如果数据库不存在一级标题，则添加进数据库，
            OneSubject = new EduSubject();
            OneSubject.setParentId("0");//一级标题默认为0
            OneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(OneSubject);
        }
        //第二列为二级分类
        //判断二级分类
        //如果一级分类，存在的话我们可以直接获取其id值，作为父类标题的id
        String pid = OneSubject.getId();
        EduSubject twoSubject = this.existTwoeSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if(twoSubject == null){
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(twoSubject);
        }

    }

    //判断一级分类不能重复添加,带入条件查询数据库中是否存在一级标题,存在则不添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        wrapper.eq("parent_id","0");//一级标题默认为0
        wrapper.eq("title",name);
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }

    //跟一级分类一样，判断二级分类
    private EduSubject existTwoeSubject(EduSubjectService eduSubjectService,String name,String id){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        wrapper.eq("parent_id",id);//判断父类id
        wrapper.eq("title",name);//判断名字是否重复
        EduSubject twoSubject = eduSubjectService.getOne(wrapper);
        return twoSubject;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
