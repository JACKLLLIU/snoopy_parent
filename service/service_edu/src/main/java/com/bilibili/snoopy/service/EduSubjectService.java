package com.bilibili.snoopy.service;

import com.bilibili.snoopy.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.snoopy.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author LJF
 * @since 2020-05-27
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllSubject();
}
