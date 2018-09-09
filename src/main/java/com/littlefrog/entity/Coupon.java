package com.littlefrog.entity;

import javax.persistence.*;
import java.util.Calendar;

@Entity(name = "coupon")
public class Coupon {
    /**
     * 优惠券id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", updatable = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer couponID;
    /**
     * 优惠券持有用户
     */
    @Column(name = "user_id", insertable = false, updatable = false, nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private Integer userID;
    /**
     * 优惠券到期时间
     */
    @Column(name = "dure_time", nullable = false)
    private Calendar dureTime;
    /**
     * 优惠券有效天数
     */
    @Transient
    private static int LAST_TIME = 7;
    /**
     * 优惠券价值
     */
    @Column(name = "amount", nullable = false, columnDefinition = "decimal(6,2) UNSIGNED")
    private double amount;
    /**
     * 是否有效
     */
    @Column(name = "is_valid", nullable = false, columnDefinition = "tinyint(1) UNSIGNED")
    private boolean isValid = true;
    /**
     * 活动（作为验证）
     */
    @Column(name = "activity_code", nullable = false, columnDefinition = "INT(11) UNSIGNED")
    private int code;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "coupon_user_key", foreignKeyDefinition = " FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE;"))
    private User user;

    public Coupon() {
    }

    public Coupon(Integer userID, double amount, int code) {
        this.userID = userID;
        Calendar now = Calendar.getInstance();
        this.dureTime = Calendar.getInstance();
        dureTime.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH) + LAST_TIME + 1, 0, 0, 0);
        this.amount = amount;
        this.code = code;
    }

    public Integer getCouponID() {
        return couponID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Calendar getDureTime() {
        return dureTime;
    }

    public void setDureTime(Calendar dureTime) {
        this.dureTime = dureTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public static int getLastTime() {
        return LAST_TIME;
    }

    public static void setLastTime(int lastTime) {
        LAST_TIME = lastTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isValid() {
        setValid();
        return isValid;
    }

    public void setValid() {
        Calendar now = Calendar.getInstance();
        isValid = now.before(dureTime);
    }

    @Override
    public String toString() {
        setValid();
        return "Coupon{" +
                "couponID=" + couponID +
                ", userID=" + userID +
                ", dureTime=" + dureTime +
                ", amount=" + amount +
                ", isValid=" + isValid +
                ", code=" + code +
                '}';
    }
}
