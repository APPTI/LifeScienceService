package com.littlefrog.service;

import com.littlefrog.entity.Course;
import com.littlefrog.entity.StudyRecord;
import com.littlefrog.respository.CourseRepository;
import com.littlefrog.respository.StudyRecordRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service ("StudyRecordService")
public class StudyRecordService {
    private StudyRecordRepository studyRecordRepository;
    private CourseRepository courseRepository;

    @Autowired
    public void setStudyRecordRepository(StudyRecordRepository studyRecordRepository){
        this.studyRecordRepository = studyRecordRepository;
    }
    @Autowired
    public void setCourseRepository(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }


    private Store store = new Store();
    public List<StudyRecord> getProgressRateList(int lessonID, int userID){
        return studyRecordRepository.getProgressRate(lessonID,userID);
    }

    public void updateProgressRate(StudyRecord studyRecord){
        studyRecordRepository.updateProgress(studyRecord.getUsr_id(), studyRecord.getLesson_id(), studyRecord.getHour(), studyRecord.getMinute(), studyRecord.getSecond(), studyRecord.getLesson_id());
    }

    public void addProgressRate (int usr_id,int course_id){
        studyRecordRepository.addProgress(usr_id,course_id);
    }
    public List<Course> getMyCourses(int userId, int index,int offset){
        List<Course> courseList = store.getMyCourseList(userId,index,offset);
        if (courseList == null){
            courseList = new ArrayList<Course>();
            List<Integer> courseIdList = studyRecordRepository.getMyCourses(userId);
            for (Integer each :courseIdList){
                courseList.add(courseRepository.findByCourseId(each));
            }
            store.addMyCourseList(userId,courseList);
        }
        return courseList.subList(index,Math.min(index+offset,courseList.size()));
    }
}
