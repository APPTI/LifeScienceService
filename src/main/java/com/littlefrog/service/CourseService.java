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
    int currentID=0;

    private Store store = new Store();

    public List<Course> getCourseByTag(Tag tag,String keyword,int index,int offset,int id){
        List<Course> courseList=store.getCourseList(id,index,offset);
        if ( courseList== null){
            store.addCourseList(id,courseRepository.findCourseByTag(tag,keyword));
        }
        return courseList;
    }

    public List<Course> getCourseByTagAndPopularity(Tag tag,String keyword,int index,int offset,int id){
        List<Course> courseList=store.getCourseList(id,index,offset);
        if ( courseList== null){
            store.addCourseList(id,courseRepository.findCourseByTagAndPopularity(tag,keyword));
        }
        return courseList;
    }

    public List<Course> getCourseByTagAndReleaseTime(Tag tag,String keyword,int index,int offset,int id){
        List<Course> courseList=store.getCourseList(id,index,offset);
        if ( courseList== null){
            store.addCourseList(id,courseRepository.findCourseByTagAndReleaseTime(tag,keyword));
        }
        return courseList;
    }

    public List<Course> getCourseByReleaseTime (String keyword,int index,int offset,int id){
        List<Course> courseList=store.getCourseList(id,index,offset);
        if ( courseList== null){
            store.addCourseList(id,courseRepository.findCourseByReleaseTime(keyword));
        }
        return courseList;
    }

    public List<Course> getCourseByPopularity (String keyword,int index,int offset,int id){
        List<Course> courseList=store.getCourseList(id,index,offset);
        if ( courseList== null){
            store.addCourseList(id,courseRepository.findCourseByPopularity(keyword));
        }
        return courseList;
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
