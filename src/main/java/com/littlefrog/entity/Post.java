package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Objects;

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
    @Column(name = "post_id", updatable = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer postID;
    /**
     * 帖子的课程
     */
    @Column(name = "course_id", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer courseID;
    /**
     * 发帖用户
     */
    @Column(name = "user_id", insertable = false, updatable = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer userID;
    /**
     * 上一被回复贴子
     */
    @Column(name = "previous_post_id", columnDefinition = "INT(11) UNSIGNED")
    private Integer previousPostID;
    /**
     * 帖子内容
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    /**
     * 赞
     */
    @Column(name = "likes", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer likes;
    /**
     * 回复数
     */
    @Column(name = "reply", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer reply;

    @Transient
    private String prePoster;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "post_user_key", foreignKeyDefinition = " FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE;"))
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPrePoster() {
        return prePoster;
    }

    public void setPrePoster(String prePoster) {
        this.prePoster = prePoster;
    }

    public Post() {
    }

    public Post(Integer courseID, Integer prePostID, String content, Integer userID) {
        this.courseID = courseID;
        this.previousPostID = prePostID;
        this.content = content;
        this.userID = userID;
        this.likes = 0;
        this.reply = 0;
    }

    public Post(Integer courseID, String content, Integer userID) {
        this.courseID = courseID;
        this.content = content;
        this.userID = userID;
        this.likes = 0;
        this.reply = 0;
    }

    public Post(Post post) {
        this.postID = post.postID;
        this.courseID = post.courseID;
        this.previousPostID = post.previousPostID;
        this.content = post.content;
        this.userID = post.userID;
        this.likes = post.likes;
        this.reply = post.reply;
        this.prePoster = post.prePoster;
    }

    public Integer getPostID() {
        return postID;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
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

    public Integer getLikes() {
        return likes;
    }

    public void addLike() {
        this.likes += 1;
    }

    public void addReply() {
        this.reply += 1;
    }

    public Integer getReply() {
        return reply;
    }


    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +
                ", courseID=" + courseID +
                ", userID=" + userID +
                ", previousPostID=" + previousPostID +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", reply=" + reply +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(postID, post.postID) &&
                Objects.equals(courseID, post.courseID) &&
                Objects.equals(userID, post.userID) &&
                Objects.equals(previousPostID, post.previousPostID) &&
                Objects.equals(content, post.content) &&
                Objects.equals(likes, post.likes) &&
                Objects.equals(reply, post.reply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID, courseID, userID, previousPostID, content, likes, reply);
    }

}
