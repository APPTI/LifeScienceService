package com.littlefrog.entity;

import com.littlefrog.common.Tag;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "course")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", updatable = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer courseID;

    @Column(nullable = false, length = 45)
    private String location;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 45)
    private String teacher;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @Column(nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private int popularity;

    @Column(nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Tag tag;

    @Column(name = "coverPic", nullable = false, length = 45)
    private String coverPic;

    @Column(name = "releaseTime", nullable = false)
    private Date releaseTime;

    @Column(nullable = false, columnDefinition = "decimal(6,2) UNSIGNED")
    private double price;

    @Column(name = "course_num", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private int courseNum;


    public Integer getCourseID() {
        return courseID;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getPopularity() {
        return popularity;
    }

    public Tag getTag() {
        return tag;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public double getPrice() {
        return price;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public void setCoverPic(String cover_pic) {
        this.coverPic = cover_pic;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public Course() {

    }

    public Course(String location, String name, String teacher, String introduction, int popularity, Tag tag, String coverPic, Date releaseTime, double price, int courseNum) {
        this.location = location;
        this.name = name;
        this.teacher = teacher;
        this.introduction = introduction;
        this.popularity = popularity;
        this.tag = tag;
        this.coverPic = coverPic;
        this.releaseTime = releaseTime;
        this.price = price;
        this.courseNum = courseNum;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", introduction='" + introduction + '\'' +
                ", popularity=" + popularity +
                ", tag=" + tag +
                ", coverPic='" + coverPic + '\'' +
                ", releaseTime=" + releaseTime +
                ", price=" + price +
                ", courseNum=" + courseNum +
                '}';
    }

}
