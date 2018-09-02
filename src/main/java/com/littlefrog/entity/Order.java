package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "userorder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "order_id")
    private Integer orderID;

    @Column(name = "course_id")
    private Integer courseID;

    @Column(name = "user_id")
    private Integer userID;

    @Column(name = "has_pay")
    private Boolean hasPay;

    @Column(name = "order_time")
    private Date orderTime;

    @Column (name = "is_recharge")
    private boolean isRecharge;

    public Order(Integer userID) {
        this.userID = userID;
        this.isRecharge = false;
        this.has_pay = false;
        this.ordertime=new Date();
    }

    public Order(Integer courseID, Integer userID, Boolean hasPay, Date orderTime) {
        this.courseID = courseID;
        this.userID = userID;
        this.hasPay = hasPay;
        this.orderTime = orderTime;
    }

    public Order(int courseID, int userID, Date date, boolean hasPay) {
        this.courseID = courseID;
        this.userID = userID;
        this.orderTime =date;
        this.hasPay =hasPay;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", courseID=" + courseID +
                ", userID=" + userID +
                ", hasPay=" + hasPay +
                ", orderTime=" + orderTime +
                '}';
    }

    public Integer getOrderID() {

        return orderID;
    }

    public boolean isRecharge() {
        return isRecharge;
    }

    public void setRecharge(boolean recharge) {
        this.isRecharge = recharge;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }


    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Boolean getHasPay() {
        return hasPay;
    }

    public void setHasPay(Boolean hasPay) {
        this.hasPay = hasPay;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
