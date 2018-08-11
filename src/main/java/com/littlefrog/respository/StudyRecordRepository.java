package com.littlefrog.respository;

import com.littlefrog.entity.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudyRecordRepository extends JpaRepository<StudyRecord,Integer> {
    @Query(value = "SELECT * from study_record u where u.course_id = ?1 and u.usr_id = ?2",nativeQuery = true)
    public List<StudyRecord> getProgressRate(int courseID, int userID);


    @Query(value = "SELECT u.course_id from study_record u where u.usr_id = ?1",nativeQuery = true)
    public List<Integer> getMyCourses(int userID);

    @Transactional
    @Modifying
    @Query (value = "delete * from study_record u where u.usr_id = ?1 and u.course_id = ?2",nativeQuery = true)
    public void deleteLastRate(int userID,int courseID);


    @Transactional
    @Modifying
    @Query(value = "UPDATE study_record t " +
            "SET t.hour = ?3 ,t.minute = ?4,t.second = ?5 ,t.lesson_id = ?6 "  +
            "where usr_id = ?1 and course_id = ?2", nativeQuery = true)
    public void updateProgress(Integer userID, Integer courseID, int hour,int minute,int second,int lesson_id);


}
