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
public class StudyRecordController {
    @Autowired
    StudyRecordService studyRecordService;

    @GetMapping("/learning-record")
    public Response getLatestLearningRecord(@RequestParam int courseId,@RequestParam int userId){
        List<StudyRecord> list = studyRecordService.getProgressRateList(courseId,userId);
        if (list == null){
            return genFailResult("获取列表失败");
        }
        else {
            return genSuccessResult(list);
        }
    }

    @PostMapping ("learning-record/add")
    public Response updateLearningProgress (@RequestParam StudyRecord record){
        try {
            studyRecordService.updateProgressRate(record);
        }
        catch (Exception e){
            return genFailResult("更新失败");
        }
        return genSuccessResult("已更新");
    }

    @GetMapping("course/my")
    public Response getMyCourse(@RequestParam int userId,@RequestParam int index,@RequestParam int offset){
        List<Course> courseList = studyRecordService.getMyCourses(userId,index,offset);
        if (courseList == null){
            return genFailResult("获取失败");
        }
        else {
            return genSuccessResult(courseList);
        }
    }
}
