package com.littlefrog.controller;


import com.littlefrog.common.Response;
import com.littlefrog.entity.Course;
import com.littlefrog.entity.StudyRecord;
import com.littlefrog.service.StudyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
@CrossOrigin
public class StudyRecordController {
    @Autowired
    StudyRecordService studyRecordService;

    @GetMapping("api/learning-record")
    public Response getLatestLearningRecord(@RequestHeader String appid,@RequestParam int courseId,@RequestParam int userId){
        List<StudyRecord> list = studyRecordService.getProgressRateList(courseId,userId);
        if (list == null){
            return genFailResult("获取列表失败");
        }
        else {
            return genSuccessResult(list);
        }
    }

    @PostMapping ("api/learning-record/add")
    public Response updateLearningProgress (@RequestHeader String appid,@RequestParam StudyRecord record){
        try {
            studyRecordService.updateProgressRate(record);
        }
        catch (Exception e){
            return genFailResult("更新失败");
        }
        return genSuccessResult("已更新");
    }

    @GetMapping("api/course/my")
    public Response getMyCourse(@RequestHeader String appid,@RequestParam int userId,@RequestParam int index,@RequestParam int offset){
        List<Course> courseList = studyRecordService.getMyCourses(userId,index,offset);
        if (courseList == null){
            return genFailResult("获取失败");
        }
        else {
            return genSuccessResult(courseList);
        }
    }
}
