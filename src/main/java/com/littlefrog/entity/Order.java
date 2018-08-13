package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "userorder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String couponid;
    @Column
    private String courseid;
    @Column
    private String userid;
    @Column
    private Boolean has_pay;
    @Column
    private Date ordertime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Boolean getHas_pay() {
        return has_pay;
    }

    public void setHas_pay(Boolean has_pay) {
        this.has_pay = has_pay;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Order(String couponid, String courseid, String userid, Boolean has_pay, Date ordertime) {
        this.couponid = couponid;
        this.courseid = courseid;
        this.userid = userid;
        this.has_pay = has_pay;
        this.ordertime = ordertime;
    }
}
