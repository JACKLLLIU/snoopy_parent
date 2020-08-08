package com.bilibili.snoopy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bilibili.snoopy.client.VodClient;
import com.bilibili.snoopy.entity.EduChapter;
import com.bilibili.snoopy.entity.EduVideo;
import com.bilibili.snoopy.entity.chapter.ChapterVo;
import com.bilibili.snoopy.entity.chapter.VideoVo;
import com.bilibili.snoopy.exceptionhandler.MyException;
import com.bilibili.snoopy.mapper.EduChapterMapper;
import com.bilibili.snoopy.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.snoopy.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author LJF
 * @since 2020-05-28
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

/*    @Autowired
    private EduChapterMapper eduChapterMapper;*/

    @Autowired
    private VodClient vodClient;

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getchapterVideoByCourseId(String courseId) {
        //根据id查询对应的章节
        QueryWrapper<EduChapter> Wrapperchapter = new QueryWrapper<EduChapter>();
        Wrapperchapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList =  baseMapper.selectList(Wrapperchapter);

        //根据课程id查询，对应的所有的小节
        QueryWrapper<EduVideo> WrapperEduVideo = new QueryWrapper<EduVideo>();
        WrapperEduVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(WrapperEduVideo);

        //定义最终存放集合
        List<ChapterVo> finalList = new ArrayList<ChapterVo>();

        for(int i = 0;i<eduChapterList.size();i++){
            EduChapter chapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            //赋值chapterVo拥有的id title
            BeanUtils.copyProperties(chapter,chapterVo);
            finalList.add(chapterVo);

            //封装小节
            List<VideoVo> videoVoList = new ArrayList<>();
            for(int x = 0;x<eduVideoList.size();x++){
                EduVideo eduVideo = eduVideoList.get(x);
                if(eduVideo.getChapterId().equals(chapterVo.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id 查询小节表，如果查询数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        //判断
        if(count >0) {//查询出小节，不进行删除
            throw new MyException(20001,"不能删除");
        } else { //不能查询数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            //成功  1>0   0>0
            return result>0;
        }
    }

    @Override
    public void removeByCourseId(String courseId) {
        //删除课程的时候还需要删除课程下章节中面对应的视频
        QueryWrapper<EduChapter> EduChapter = new QueryWrapper<>();
        EduChapter.eq("course_id",courseId);
        baseMapper.delete(EduChapter);
    }
}
