package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_num")
    private int order;

    @Column
    private String title;

    @Column
    private String video_url;

    @Column
    private String description;

    @Column(name = "cover_pic")
    private String cover_url;

    @Column
    private int course_id;

    @Column
    private Date time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Lesson(int order, String title,String video_url, String description, String cover_url, int course_id, Date time) {
        this.title = title;
        this.order = order;
        this.video_url = video_url;
        this.description = description;
        this.cover_url = cover_url;
        this.course_id = course_id;
        this.time = time;
    }


    public Lesson() {

    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", order=" + order +
                ", video_url='" + video_url + '\'' +
                ", description='" + description + '\'' +
                ", cover_url='" + cover_url + '\'' +
                ", course_id=" + course_id +
                ", time=" + time +
                '}';
    }
}
