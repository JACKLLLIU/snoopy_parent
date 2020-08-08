package com.bilibili.snoopy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bilibili.snoopy.client.VodClient;
import com.bilibili.snoopy.entity.EduVideo;
import com.bilibili.snoopy.mapper.EduVideoMapper;
import com.bilibili.snoopy.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author LJF
 * @since 2020-05-28
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;


    @Override
    public void removeByCourseId(String courseId) {
        //删除课程的同时删除下面所有的视频
        //先使用课程id查询出所有的video表中所有的字段的Videosouceid
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");

        //查询到的可能不止一个视频
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);
        //将List<EduVideo>转化为 List<String>的传参形式
        List<String> targetList = new ArrayList<>();
        for(EduVideo video : eduVideoList){
            String id = video.getVideoSourceId();
            if(!StringUtils.isEmpty(id)){
                targetList.add(id);
            }
        }
        //删除视频
        if(targetList.size()>0){
            vodClient.deleteBatch(targetList);
        }

        QueryWrapper<EduVideo> EduVideo = new QueryWrapper<>();
        EduVideo.eq("course_id",courseId);
        baseMapper.delete(EduVideo);
    }

}
