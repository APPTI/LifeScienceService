package com.littlefrog.controller;


import com.littlefrog.common.Response;
import com.littlefrog.entity.Lesson;
import com.littlefrog.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
public class LessonController {
    @Autowired
    private LessonService lessonService;

    private int currentID = 0;

    @GetMapping("course/lesson")
    public Response getCourseLessons(@RequestParam int courseId,@RequestParam int index,@RequestParam int offset){
        List<Lesson> lessonList = lessonService.getLessonsByCourseID(courseId,currentID++,index,offset);
        if (lessonList  == null){
            return genFailResult("获取列表失败");
        }
        else {
            return genSuccessResult(lessonList);
        }
    }

}
