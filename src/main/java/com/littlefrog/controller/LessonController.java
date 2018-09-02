package com.littlefrog.controller;


import com.littlefrog.common.Response;
import com.littlefrog.entity.Lesson;
import com.littlefrog.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
@CrossOrigin
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @Value("${appID}")
    private String appID;

    private int currentID = 0;

    @GetMapping("api/course/lesson")
    public Response getCourseLessons(@RequestHeader String appID,@RequestParam int courseID,@RequestParam int index,@RequestParam int offset){
        if(!appID.equals(this.appID)){
            return genFailResult("错误的appID");
        }
        List<Lesson> lessonList = lessonService.getLessonsByCourseID(courseID,currentID++,index,offset);
        if (lessonList  == null){
            return genFailResult("获取列表失败");
        } else {
            return genSuccessResult(lessonList);
        }
    }

    @PostMapping("api/lesson/addLesson")
    public Response addLesson(@RequestHeader String appID,@RequestParam int courseID,@RequestParam int order,@RequestParam String title,@RequestParam String videoUrl,@RequestParam String description,@RequestParam String coverUrl){
        Lesson lesson = lessonService.addLesson(courseID,order,title,videoUrl,description,coverUrl);
        if(lesson==null){
            return genFailResult("添加课程失败");
        }else{
            return genSuccessResult(lesson);
        }
    }

    @PostMapping("api/lesson/setLessonInfo")
    public Response setLesson(@RequestHeader String appID,@RequestParam int ID,@RequestParam String title,@RequestParam String videoUrl,@RequestParam String description,@RequestParam String coverUrl){
        boolean result=lessonService.setLessonInfo(ID,title,videoUrl,description,coverUrl);
        if(!result){
            return genFailResult("设置课程信息失败！");
        }else{
            Optional<Lesson> lesson=lessonService.FindById(ID);
            if(!lesson.isPresent()){
                return genFailResult("服务器查找数据失败，请重试");
            }else{
                return genSuccessResult(lesson);
            }
        }
    }

    @PostMapping("api/lesson/setVideoURL")
    public Response setVideoURL(@RequestHeader String appID,@RequestParam int ID,@RequestParam String videoUrl){
        boolean result=lessonService.setVideoUrl(ID,videoUrl);
        if(!result){
            return genFailResult("设置视频url失败！");
        }else{
            Optional<Lesson> lesson=lessonService.FindById(ID);
            if(!lesson.isPresent()){
                return genFailResult("服务器查找数据失败，请重试");
            }else{
                return genSuccessResult(lesson);
            }
        }
    }

    @PostMapping("api/lesson/SetCoverPic")
    public Response SetCoverPic(@RequestHeader String appID,@RequestParam int id,@RequestParam String url){
        boolean result=lessonService.setCoverPic(id,url);
        if(!result){
            return genFailResult("设置封面url失败！");
        }else{
            Optional<Lesson> lesson=lessonService.FindById(id);
            if(!lesson.isPresent()){
                return genFailResult("服务器查找数据失败，请重试");
            }else{
                return genSuccessResult(lesson);
            }
        }
    }
}
