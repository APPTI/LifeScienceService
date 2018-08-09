package com.littlefrog.service;

import com.littlefrog.common.Tag;
import com.littlefrog.entity.Course;
import com.littlefrog.respository.CourseRepository;
import com.littlefrog.respository.UserRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("CourseService")
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    private Store store = new Store();
    public Course getCourse (int id){
        Course course =store.getCourseById(id);
        if (course == null){
            course = courseRepository.FindById(id);
            if (course == null){
                return null;
            }
            else {
                store.addCourse(course);
            }
        }
        return course;
    }

    public Course addCourse (Integer id,String location, String name, String teacher, String introduction, int popularity, Tag tag, String coverPic, Date releaseTime, double price, int courseNum){
        Course course = courseRepository.save(new Course(id,location,name,teacher,introduction,popularity,tag,coverPic,releaseTime,price,courseNum));
        return course;
    }


    public boolean deleteCourse(Integer id){
        try {
            courseRepository.deleteCourse(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
