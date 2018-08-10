package com.littlefrog.service;

import com.littlefrog.common.Tag;
import com.littlefrog.entity.Course;
import com.littlefrog.respository.CourseRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("CourseService")
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    List<Course> courseList;

    private Store store = new Store();
    public Course getCourseById (int id){
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

    public List<Course> getCourseByTag(Tag tag,int index,int offset){
        if (index == 0)
            courseList = courseRepository.findCourseByTag(tag, "");
        return courseList.subList(index,Math.min(index+offset-1,courseList.size()));
    }

    public List<Course> getCourseByTagAndPopularity(Tag tag,int index,int offset){
        if (index == 0){
            courseList = courseRepository.findCourseByTagAndPopularity(tag, "");
        }
        return courseList.subList(index,Math.min(index+offset-1,courseList.size()));
    }

    public List<Course> getCourseByTagAndReleaseTime(Tag tag,int index,int offset){
        if (index == 0)
        {
            courseList = courseRepository.findCourseByTagAndReleaseTime(tag, "");
        }
        return courseList.subList(index,Math.min(index+offset-1,courseList.size()));
    }

    public List<Course> getCourseByReleaseTime (int index,int offset){
        if (index == 0){
            courseList=courseRepository.findCourseByReleaseTime("");
        }
        return courseList.subList(index,Math.min(index+offset-1,courseList.size()));
    }

    public List<Course> getCourseByPopularity (int index,int offset){
        if (index == 0){
            courseList=courseRepository.findCourseByPopularity("");
        }
        return courseList.subList(index,Math.min(index+offset-1,courseList.size()));
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
