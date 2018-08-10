package com.littlefrog.service;

import com.littlefrog.entity.Lesson;
import com.littlefrog.respository.LessonRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
