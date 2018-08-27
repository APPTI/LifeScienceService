package com.littlefrog.service;

import com.littlefrog.entity.Lesson;
import com.littlefrog.respository.LessonRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("LessonService")
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    private Store store= new Store();
    public List<Lesson> getLessonsByCourseID(int courseID,int id,int index,int offset){
        List<Lesson> lessonList=store.getLessonList(id,index,offset);
        if ( lessonList== null){
            lessonList = lessonRepository.findAll(courseID);
            store.addLessonList(id,lessonList);
        }
        return lessonList;
    }
    public Lesson AddLesson(int courseId,int order,String title, String video_url, String description, String cover_url){
        Lesson lesson = lessonRepository.save(new Lesson(order,title,video_url,description,cover_url,courseId,new Date()));
        if(lesson == null){
            return null;
        }else{
            return lesson;
        }
    }
    public Optional<Lesson> FindById(int id){
        return lessonRepository.findById(id);
    }
    public boolean setLessonInfo(int id,String title, String video_url, String description, String cover_url){
        try {
            lessonRepository.setLessonInfo(id,title,video_url,description,cover_url);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteById(int id){
        try {
            lessonRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteByCourseId(int courseId){
        try {
            lessonRepository.deleteLessonsByCourse_id(courseId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean setVideoUrl(int id, String Url){
        try {
            lessonRepository.setVedioUrl(id,Url);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean setCoverPic(int id, String Url){
        try {
            lessonRepository.setCover_url(id,Url);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
