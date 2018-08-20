package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "userorder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer couponid;
    @Column
    private Integer courseid;
    @Column
    private Integer userid;
    @Column
    private Boolean has_pay;
    @Column
    private Date ordertime;
    @Column
    private boolean is_recharge;

    public Order(Integer userid) {
        this.userid = userid;
        this.is_recharge = false;
    }

    public Order(Integer couponid, Integer courseid, Integer userid, Boolean has_pay, Date ordertime) {
        this.couponid = couponid;
        this.courseid = courseid;
        this.userid = userid;
        this.has_pay = has_pay;
        this.ordertime = ordertime;
    }

    public Order(int courseid, int userid, Date date,boolean hasPay) {
        this.courseid=courseid;
        this.userid=userid;
        this.ordertime=date;
        this.has_pay=hasPay;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", couponid=" + couponid +
                ", courseid=" + courseid +
                ", userid=" + userid +
                ", has_pay=" + has_pay +
                ", ordertime=" + ordertime +
                '}';
    }

    public Integer getId() {

        return id;
    }

    public boolean isIs_recharge() {
        return is_recharge;
    }

    public void setIs_recharge(boolean is_recharge) {
        this.is_recharge = is_recharge;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponid() {
        return couponid;
    }

    public void setCouponid(Integer couponid) {
        this.couponid = couponid;
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
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
}
