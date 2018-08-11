package com.littlefrog.entity;

import javax.persistence.*;

@Entity(name = "progress_rate")
public class StudyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer usr_id;

    @Column
    private Integer course_id;

    @Column
    private Integer lesson_id;

    @Column
    private Integer hour;

    @Column
    private Integer minute;

    @Column
    private Integer second;


    public StudyRecord() {
    }

    public StudyRecord(Integer usr_id, Integer course_id, Integer lesson_id, Integer hour, Integer minute, Integer second) {
        this.usr_id = usr_id;
        this.course_id = course_id;
        this.lesson_id = lesson_id;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    @Override
    public String toString() {
        return "StudyRecord{" +
                "id=" + id +
                ", usr_id=" + usr_id +
                ", course_id=" + course_id +
                ", lesson_id=" + lesson_id +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsr_id() {
        return usr_id;
    }

    public void setUsr_id(Integer usr_id) {
        this.usr_id = usr_id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public Integer getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(Integer lesson_id) {
        this.lesson_id = lesson_id;
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
