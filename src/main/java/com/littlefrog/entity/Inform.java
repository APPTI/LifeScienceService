package com.littlefrog.entity;

import com.littlefrog.common.Category;

import javax.persistence.*;
import java.util.Calendar;

/**
 * @author DW
 */
@Entity(name = "inform")
public class Inform {
    /**
     * 通知id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inform_id", updatable = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer informID;
    /**
     * 被通知用户
     */
    @Column(name = "user_id", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer userID;
    /**
     * 通知内容
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    /**
     * 通知发送时间
     */
    @Column(name = "send_time", nullable = false)
    private Calendar sendTime;
    /**
     * 通知来源页面
     */
    @Column(name = "category", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Category category;
    /**
     * 返回的相关id
     */
    @Column(name = "return_id", columnDefinition = "INT(11) UNSIGNED")
    private Integer returnID;


    public Inform() {
    }

    public Inform(Integer userID, String content, Category category, Integer returnID) {
        this.userID = userID;
        this.content = content;
        sendTime = Calendar.getInstance();
        this.category = category;
        if (returnID != null) {
            this.returnID = returnID;
        }
    }

    public Integer getInformID() {
        return informID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getSendTime() {
        return sendTime;
    }

    public void setSendTime(Calendar sendTime) {
        this.sendTime = sendTime;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getReturnID() {
        return returnID;
    }

    public void setReturnID(Integer returnID) {
        this.returnID = returnID;
    }

    @Override
    public String toString() {
        return "Inform{" +
                "informID=" + informID +
                ", userID=" + userID +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                ", category=" + category +
                ", returnID=" + returnID +
                '}';
    }
}
