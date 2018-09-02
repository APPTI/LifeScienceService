package com.littlefrog.entity;

import javax.persistence.*;

@Entity(name = "progress_rate")
public class StudyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "study_record_id")
    private Integer studyRecordID;

    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "course_id")
    private Integer courseID;

    @Column(name = "lesson_id")
    private Integer lessonID;

    @Column
    private Integer hour;

    @Column
    private Integer minute;

    @Column
    private Integer second;


    public StudyRecord() {
    }

    public StudyRecord(Integer userID, Integer courseID, Integer lessonID, Integer hour, Integer minute, Integer second) {
        this.userID = userID;
        this.courseID = courseID;
        this.lessonID = lessonID;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    @Override
    public String toString() {
        return "StudyRecord{" +
                "studyRecordID=" + studyRecordID +
                ", userID=" + userID +
                ", courseID=" + courseID +
                ", lessonID=" + lessonID +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }

    public Integer getStudyRecordID() {
        return studyRecordID;
    }

    public void setStudyRecordID(Integer studyRecordID) {
        this.studyRecordID = studyRecordID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public Integer getLessonID() {
        return lessonID;
    }

    public void setLessonID(Integer lessonID) {
        this.lessonID = lessonID;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }
}
