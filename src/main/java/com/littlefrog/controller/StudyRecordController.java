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

    @GetMapping("api/learningRecord")
    public Response getLatestLearningRecord(@RequestHeader String appID,@RequestParam int courseID,@RequestParam int userID){
        List<StudyRecord> list = studyRecordService.getProgressRateList(courseID,userID);
        if (list == null){
            return genFailResult("获取列表失败");
        }
        else {
            return genSuccessResult(list);
        }
    }

    @PostMapping ("api/learningRecord/add")
    public Response updateLearningProgress (@RequestHeader String appID,@RequestParam StudyRecord record){
        try {
            studyRecordService.updateProgressRate(record);
        }
        catch (Exception e){
            return genFailResult("更新失败");
        }
        return genSuccessResult("已更新");
    }

    @GetMapping("api/course/my")
    public Response getMyCourse(@RequestHeader String appID,@RequestParam int userID,@RequestParam int index,@RequestParam int offset){
        List<Course> courseList = studyRecordService.getMyCourses(userID,index,offset);
        if (courseList == null){
            return genFailResult("获取失败");
        }
        else {
            return genSuccessResult(courseList);
        }
    }
}
