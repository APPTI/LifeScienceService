package com.littlefrog.entity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column
    private String openid;
    @Column
    private int gender;
    @Column
    private String phonenum;
    @Column
    private double balance;
    @Column
    private Date lastlogintime;
    @Column
    private boolean phonevalid;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "union_id")
    private String unionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public User(){
        setLastlogintime(new Date());
    }
    public User(String name, String openID, int gender, String phoneNum, double balance,String sessionId,String unionId) {
        this.name = name;
        this.openid = openID;
        this.gender = gender;
        this.phonenum = phoneNum;
        this.balance = balance;
        this.lastlogintime = new Date();
        this.phonevalid = true;
        this.sessionId = sessionId;
        this.unionId = unionId;
    }

    public User(String sessionId, String openID) {
        this.name = "null";
        this.openid = openID;
        this.sessionId = sessionId;
        this.gender = 0;
        this.balance = 0;
        this.phonevalid=false;
        this.lastlogintime = new Date();
    }

    public User(String sessionId, String openID,String unionId) {
        this.name = "null";
        this.openid = openID;
        this.sessionId = sessionId;
        this.unionId = unionId;
        this.gender = 0;
        this.balance = 0;
        this.phonevalid=false;
        this.lastlogintime = new Date();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }



    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", openid='" + openid + '\'' +
                ", gender=" + gender +
                ", phonenum='" + phonenum + '\'' +
                ", balance=" + balance +
                ", lastlogintime=" + lastlogintime +
                ", phonevalid=" + phonevalid +
                ", sessionId='" + sessionId + '\'' +
                ", unionId='" + unionId + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public boolean isPhonevalid() {
        return phonevalid;
    }

    public void setPhonevalid(boolean phonevalid) {
        this.phonevalid = phonevalid;
    }
}
