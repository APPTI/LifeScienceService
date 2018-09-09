package com.littlefrog.entity;

import javax.persistence.*;

@Entity(name = "activity_record")
public class ActivityRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer id;

    @Column(name = "user_id", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private int userID;

    @Column(name = "activity_id", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private int activityID;

    public Integer getId() {
        return id;
    }

    public ActivityRecord() {
    }

    public ActivityRecord(int userID, int activityID) {

        this.userID = userID;
        this.activityID = activityID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }
}
