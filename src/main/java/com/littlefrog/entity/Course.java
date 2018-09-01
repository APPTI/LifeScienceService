package com.littlefrog.entity;

import com.littlefrog.common.Tag;

import javax.persistence.*;
import java.util.Date;

@Entity (name = "course")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Course(){

    }
    public Course(String location, String name, String teacher, String introduction, int popularity, Tag tag, String cover_pic, Date release_time, double price, int course_num) {
        this.location = location;
        this.name = name;
        this.teacher = teacher;
        this.introduction = introduction;
        this.popularity = popularity;
        this.tag = tag;
        this.cover_pic = cover_pic;
        this.release_time = release_time;
        this.price = price;
        this.course_num = course_num;
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
                ", release_time=" + release_time +
                ", price=" + price +
                ", course_num=" + course_num +
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
    private Date release_time;

    @Column
    private double price;

    @Column
    private int course_num;


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

    public Date getRelease_time() {
        return release_time;
    }

    public double getPrice() {
        return price;
    }

    public int getCourse_num() {
        return course_num;
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

    public void setRelease_time(Date release_time) {
        this.release_time = release_time;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCourse_num(int course_num) {
        this.course_num = course_num;
    }
}
