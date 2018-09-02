package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "user_id")
    private Integer userID;

    @Column
    private String name;

    @Column(name = "open_id")
    private String openID;

    @Column
    private int gender;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column
    private double balance;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column (name = "phone_valid")
    private boolean phoneValid;

    @Column(name = "session_id")
    private String sessionID;

    @Column(name = "union_id")
    private String unionID;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getUnionID() {
        return unionID;
    }

    public void setUnionID(String unionID) {
        this.unionID = unionID;
    }

    public User(){
        setLastLoginTime(new Date());
    }
    public User(String name, String openID, int gender, String phoneNum, double balance, String sessionID, String unionID) {
        this.name = name;
        this.openID = openID;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.balance = balance;
        this.lastLoginTime = new Date();
        this.phoneValid = true;
        this.sessionID = sessionID;
        this.unionID = unionID;
    }

    public User(String sessionID, String openID) {
        this.name = "null";
        this.openID = openID;
        this.sessionID = sessionID;
        this.gender = 0;
        this.balance = 0;
        this.phoneValid =false;
        this.lastLoginTime = new Date();
    }

    public User(String sessionID, String openID, String unionID) {
        this.name = "null";
        this.openID = openID;
        this.sessionID = sessionID;
        this.unionID = unionID;
        this.gender = 0;
        this.balance = 0;
        this.phoneValid =false;
        this.lastLoginTime = new Date();
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
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", openID='" + openID + '\'' +
                ", gender=" + gender +
                ", phoneNum='" + phoneNum + '\'' +
                ", balance=" + balance +
                ", lastLoginTime=" + lastLoginTime +
                ", phoneValid=" + phoneValid +
                ", sessionID='" + sessionID + '\'' +
                ", unionID='" + unionID + '\'' +
                '}';
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getOpenID() {
        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isPhoneValid() {
        return phoneValid;
    }

    public void setPhoneValid(boolean phoneValid) {
        this.phoneValid = phoneValid;
    }
}
