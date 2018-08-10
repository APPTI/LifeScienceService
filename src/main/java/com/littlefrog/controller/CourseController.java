package com.littlefrog.controller;
import com.littlefrog.common.Response;
import com.littlefrog.common.Tag;
import com.littlefrog.entity.Course;
import com.littlefrog.entity.User;
import com.littlefrog.respository.UserRepository;
import com.littlefrog.service.CourseService;
import com.littlefrog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    private Response response;

    private int currentID=0;
    @GetMapping("course/by-keyword")
    public Response getCoursesByKeyword(@RequestParam String keyword, @RequestParam int sortBy, @RequestParam Tag tag, @RequestParam int index, @RequestParam int offset){
        List<Course>  courseList = null;
        switch (sortBy){
            case 0:
                if (tag == Tag.NONE){
                    courseList = courseService.getCourseByReleaseTime(keyword,index,offset,currentID++);
                }
                else {
                    courseList = courseService.getCourseByTagAndReleaseTime(tag,keyword,index,offset,currentID++);
                }
                break;
            case 1:
                courseList=courseService.getCourseByTag(tag,keyword,index,offset,currentID);
                break;
            case 2:
                if (tag == Tag.NONE){
                    courseList = courseService.getCourseByPopularity(keyword,index,offset,currentID++);
                }
                else {
                    courseList = courseService.getCourseByTagAndPopularity(tag,keyword,index,offset,currentID++);
                }
                break;
            default:
                courseList = null;
        }
        //courseList= courseService.getAllCourse();
        if (courseList  == null){
            return genFailResult("获取列表失败");
        }
        else {
            return genSuccessResult(courseList);
        }
    }

}
