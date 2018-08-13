package com.littlefrog.entity;

import com.littlefrog.common.Page;

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
    private Integer informID;
    /**
     * 被通知用户
     */
    @Column
    private Integer userID;
    /**
     * 通知内容
     */
    @Column
    private String content;
    /**
     * 通知发送时间
     */
    @Column
    private Calendar sendTime;
    /**
     * 通知来源页面
     */
    @Column
    private Page page;
    /**
     *返回的相关id
     */
    @Column(name = "return_id")
    private Integer returnID;


    public Inform() {
    }

    public Inform(Integer userID, String content, Page page,int returnID) {
        this.userID = userID;
        this.content = content;
        sendTime = Calendar.getInstance();
        this.page = page;
        this.returnID=returnID;
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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
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
                ", page=" + page +
                ", returnID=" + returnID +
                '}';
    }
}
