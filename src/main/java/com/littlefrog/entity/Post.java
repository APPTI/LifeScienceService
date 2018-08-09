package com.littlefrog.entity;

import javax.persistence.*;

/**
 * @author DW
 */
@Entity(name = "post")
public class Post {
    /**
     * 帖子id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postID;
    /**
     * 帖子的课程
     */
    @Column
    private Integer lessonID;
    /**
     * 发帖用户
     */
    @Column
    private Integer userID;
    /**
     * 上一被回复贴子
     */
    @Column
    private Integer previousPostID;
    /**
     * 帖子内容
     */
    @Column
    private String content;



    public Post() {
    }

    public Post(Integer lessonID, Integer prePostID, String content, Integer userID) {
        this.lessonID = lessonID;
        this.previousPostID = prePostID;
        this.content = content;
        this.userID = userID;
    }

    public Post(Integer lessonID, String content, Integer userID) {
        this.lessonID = lessonID;
        this.content = content;
        this.userID = userID;
    }

    public Post(Post post) {
        this.postID = post.postID;
        this.lessonID = post.lessonID;
        this.previousPostID = post.previousPostID;
        this.content = post.content;
        this.userID = post.userID;
    }

    public Integer getPostID() {
        return postID;
    }

    public Integer getLessonID() {
        return lessonID;
    }

    public void setLessonID(Integer lessonID) {
        this.lessonID = lessonID;
    }

    public Integer getPreviousPostID() {
        return previousPostID;
    }

    public void setPreviousPostID(Integer previousPostID) {
        this.previousPostID = previousPostID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +
                ", lessonID=" + lessonID +
                ", previousPostID=" + previousPostID +
                ", content='" + content + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }


}
