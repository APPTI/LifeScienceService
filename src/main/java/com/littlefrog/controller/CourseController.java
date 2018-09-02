package com.littlefrog.controller;

import com.littlefrog.common.Response;
import com.littlefrog.common.Tag;
import com.littlefrog.entity.Course;
import com.littlefrog.service.CourseService;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    private Response response;

    @Value("${appID}")
    private String appID;

    private int currentID=0;
    @GetMapping("api/course/byKeyword")
    public Response getCoursesByKeyword(@RequestHeader String appID,@RequestParam String keyword, @RequestParam int sortBy, @RequestParam Tag tag, @RequestParam int index, @RequestParam int offset){
        if(!appID.equals(this.appID)){
            return genFailResult("错误的appID");
        }
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

        if (courseList  == null){
            return genFailResult("获取列表失败");
        }
        else {
            return genSuccessResult(courseList);
        }
    }

    @GetMapping("api/course")
    public Response getAllCourses(@RequestHeader String appID,@RequestParam  int index,@RequestParam int offset){
        if(!appID.equals(this.appID)){
            return genFailResult("错误的appID");
        }
        List<Course> courseList= courseService.getAllCourse(index,offset,currentID++);
        if (courseList  == null){
            return genFailResult("获取列表失败");
        }
        else {
            return genSuccessResult(courseList);
        }
    }

    @GetMapping("api/course/findCourse")
    public  Response findCourse(@RequestHeader String appID,@RequestParam int courseID){
        if(!appID.equals(this.appID)){
            return genFailResult("错误的appID");
        }
        Course c = courseService.findByID(courseID);
        if (c==null){
            return genFailResult("获取course失败");
        }else {
            return genSuccessResult(c);
        }
    }

    @PostMapping("api/course/addCourse")
    public Response updateCourse(@RequestHeader String appID, @RequestParam String name, @RequestParam String location, @RequestParam String teacher, @RequestParam String introduction, @RequestParam int popularity, @RequestParam Tag tag, @RequestParam String coverPic, @RequestParam double price , @RequestParam int courseNum){
        if(!appID.equals(this.appID)){
            return genFailResult("错误的appID");
        }
        Course course=courseService.addCourse(location,name,teacher,introduction,popularity,tag,coverPic,new Date(),price,courseNum);
        if(course==null){
            return genFailResult("添加课程失败");
        }else{
            return genSuccessResult(course);
        }
    }

    @PostMapping("api/course/setCourseInfo")
    public Response updateCourse(@RequestHeader String appID, @RequestParam Integer ID, @RequestParam String name, @RequestParam String location, @RequestParam String teacher, @RequestParam String introduction, @RequestParam int popularity, @RequestParam Tag tag, @RequestParam String coverPic, @RequestParam double price , @RequestParam int courseNum){
        if(!appID.equals(this.appID)){
            return genFailResult("错误的appID");
        }
        boolean result=courseService.setCourseInfo(ID,location,name,teacher,introduction,popularity,tag,coverPic,price,courseNum);
        if(!result){
            return genFailResult("修改课程信息失败");
        }else{
            Course course=courseService.findByID(ID);
            return genSuccessResult(course);
        }
    }

    @PostMapping("api/course/setCoverPic")
    public Response setCoverPic(@RequestHeader String appID,@RequestParam int ID,@RequestParam String coverPic){
        if(!appID.equals(this.appID)){
            return genFailResult("错误的appID");
        }
        boolean result = courseService.setCoverPic(ID, coverPic);
        if(!result){
            return genFailResult("设置封面图片失败");
        }else{
            Course course=courseService.findByID(ID);
            return genSuccessResult(course);
        }
    }
}
