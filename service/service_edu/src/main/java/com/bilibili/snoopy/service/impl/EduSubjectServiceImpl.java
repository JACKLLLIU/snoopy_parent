package com.bilibili.snoopy.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bilibili.snoopy.Listener.SubjectExcelListener;
import com.bilibili.snoopy.entity.EduSubject;
import com.bilibili.snoopy.entity.excel.SubjectData;
import com.bilibili.snoopy.entity.subject.OneSubject;
import com.bilibili.snoopy.entity.subject.TwoSubject;
import com.bilibili.snoopy.mapper.EduSubjectMapper;
import com.bilibili.snoopy.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author LJF
 * @since 2020-05-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllSubject() {
        //查询所有的1级分类何2二级分类的数据
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<EduSubject>();
        wrapper1.eq("parent_id","0");
        //wrapper1.orderByAsc("sort");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapper1);

        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<EduSubject>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapper2);

        //数据保存，封装
        List<OneSubject> finalSubjectList = new ArrayList<OneSubject>();

        //将一级分类的数据填充到finalSubjectList
        for(int i = 0;i<oneSubjectList.size();i++){
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());
            finalSubjectList.add(oneSubject);
        }

        //遍历finalSubjectList中的oneSubject子类的数据
        for(int i = 0;i<finalSubjectList.size();i++){
            List<TwoSubject> list = new ArrayList<TwoSubject>();
            for(int x=0;x<twoSubjectList.size();x++){
                if(finalSubjectList.get(i).getId().equals(twoSubjectList.get(x).getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    twoSubject.setId(twoSubjectList.get(x).getId());
                    twoSubject.setTitle(twoSubjectList.get(x).getTitle());
                    list.add(twoSubject);
                }
            }
            finalSubjectList.get(i).setChildren(list);
        }
        System.out.println(finalSubjectList);
        return finalSubjectList;
    }
}
