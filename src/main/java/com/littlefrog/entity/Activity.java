package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name="activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;//活动标题

    @Column
    private String cover_url;//封面图片

    public Activity(String title, String cover_url, String content, Boolean isHasCoupon, int coupon, int couponExpiry, Date expiry, int requirement, int courseId, int ammount) {
        this.title = title;
        this.cover_url = cover_url;
        this.content = content;
        this.isHasCoupon = isHasCoupon;
        this.coupon = coupon;
        this.couponExpiry = couponExpiry;
        this.expiry = expiry;
        this.requirement = requirement;
        this.courseId = courseId;
        this.ammount = ammount;
    }

    public String getCover_url() {

        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    @Column
    private String content;//活动内容，为svg格式的图片url

    @Column(name = "is_has_coupon")
    private Boolean  isHasCoupon;//是否有代金券

    @Column
    private int coupon;//代金券面值

    @Column(name = "coupon_expiry")
    private int couponExpiry;//代金券有效期

    @Column
    private Date expiry;//有效期

    @Column
    private int requirement;//获取优惠券条件，0 为分享， 1为报名课程， 2 为充值

    @Column(name = "courseid")
    private int  courseId;//关联的课程id

    @Column
    private int  ammount;//充值金额

    public int getCouponExpiry() {
        return couponExpiry;
    }

    public void setCouponExpiry(int couponExpiry) {
        this.couponExpiry = couponExpiry;
    }

    public Activity(String title, String content, Boolean isHasCoupon, int coupon, int couponExpiry, Date expiry, int requirement, int courseId, int ammount) {
        this.title = title;
        this.content = content;
        this.isHasCoupon = isHasCoupon;
        this.coupon = coupon;
        this.couponExpiry = couponExpiry;
        this.expiry = expiry;
        this.requirement = requirement;
        this.courseId = courseId;
        this.ammount = ammount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getHasCoupon() {
        return isHasCoupon;
    }

    public void setHasCoupon(Boolean hasCoupon) {
        isHasCoupon = hasCoupon;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public int getRequirement() {
        return requirement;
    }

    public void setRequirement(int requirement) {
        this.requirement = requirement;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isHasCoupon=" + isHasCoupon +
                ", coupon=" + coupon +
                ", couponExpiry=" + couponExpiry +
                ", expiry=" + expiry +
                ", requirement=" + requirement +
                ", courseId=" + courseId +
                ", ammount=" + ammount +
                '}';
    }
}
