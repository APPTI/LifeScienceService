package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name="activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "activity_id")
    private Integer activityID;

    @Column
    private String title;//活动标题

    @Column(name = "cover_url")
    private String coverUrl;//封面图片

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

    @Column(name = "course_id")
    private int courseID;//关联的课程id

    @Column
    private int amount;//充值金额

    public int getCouponExpiry() {
        return couponExpiry;
    }

    public void setCouponExpiry(int couponExpiry) {
        this.couponExpiry = couponExpiry;
    }

    public Activity(String title, String content, Boolean isHasCoupon, int coupon, int couponExpiry, Date expiry, int requirement, int courseID, int amount) {
        this.title = title;
        this.content = content;
        this.isHasCoupon = isHasCoupon;
        this.coupon = coupon;
        this.couponExpiry = couponExpiry;
        this.expiry = expiry;
        this.requirement = requirement;
        this.courseID = courseID;
        this.amount = amount;
    }

    public Integer getActivityID() {
        return activityID;
    }

    public void setActivityID(Integer activityID) {
        this.activityID = activityID;
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

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityID=" + activityID +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isHasCoupon=" + isHasCoupon +
                ", coupon=" + coupon +
                ", couponExpiry=" + couponExpiry +
                ", expiry=" + expiry +
                ", requirement=" + requirement +
                ", courseID=" + courseID +
                ", amount=" + amount +
                '}';
    }
    public Activity(String title, String coverUrl, String content, Boolean isHasCoupon, int coupon, int couponExpiry, Date expiry, int requirement, int courseID, int amount) {
        this.title = title;
        this.coverUrl = coverUrl;
        this.content = content;
        this.isHasCoupon = isHasCoupon;
        this.coupon = coupon;
        this.couponExpiry = couponExpiry;
        this.expiry = expiry;
        this.requirement = requirement;
        this.courseID = courseID;
        this.amount = amount;
    }

    public String getCoverUrl() {

        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}
