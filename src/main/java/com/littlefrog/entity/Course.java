package com.littlefrog.entity;

import com.littlefrog.common.Tag;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity (name = "course")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Course(){

    }
    public Course(String location, String name, String teacher, String introduction, int popularity, Tag tag, String cover_pic, Date releaseTime, double price, int courseNum) {
        this.location = location;
        this.name = name;
        this.teacher = teacher;
        this.introduction = introduction;
        this.popularity = popularity;
        this.tag = tag;
        this.cover_pic = cover_pic;
        this.releaseTime = releaseTime;
        this.price = price;
        this.courseNum = courseNum;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", introduction='" + introduction + '\'' +
                ", popularity=" + popularity +
                ", tag=" + tag +
                ", cover_pic='" + cover_pic + '\'' +
                ", releaseTime=" + releaseTime +
                ", price=" + price +
                ", courseNum=" + courseNum +
                '}';
    }

    @Column
    private String location;

    @Column
    private String name;

    @Column
    private String teacher;

    @Column
    private String introduction;

    @Column
    private int popularity;

    @Column
    private Tag tag;

    @Column
    private String cover_pic;

    @Column
    private Date releaseTime;

    @Column
    private double price;

    @Column
    private int courseNum;


    public Integer getId() {
        return id;
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
        return cover_pic;
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

    public void setId(Integer id) {
        this.id = id;
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
        this.cover_pic = cover_pic;
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
}
