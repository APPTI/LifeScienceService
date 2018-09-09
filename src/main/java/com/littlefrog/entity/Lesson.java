package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id", updatable = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer lessonID;

    @Column(name = "order_num", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private int order;

    @Column(nullable = false, length = 45)
    private String title;

    @Column(name = "video_url", length = 45, nullable = false)
    private String videoUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "cover_pic", nullable = false, length = 45)
    private String coverPic;

    @Column(name = "course_id", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private int courseID;

    @Column
    private Date time;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLessonID() {
        return lessonID;
    }

    public void setLessonID(Integer lessonID) {
        this.lessonID = lessonID;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Lesson(int order, String title, String videoUrl, String description, String cover_url, int courseID, Date time) {
        this.title = title;
        this.order = order;
        this.videoUrl = videoUrl;
        this.description = description;
        this.coverPic = cover_url;
        this.courseID = courseID;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonID=" + lessonID +
                ", order=" + order +
                ", title='" + title + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", description='" + description + '\'' +
                ", coverPic='" + coverPic + '\'' +
                ", courseID=" + courseID +
                ", time=" + time +
                '}';
    }

    public Lesson() {

    }

}
