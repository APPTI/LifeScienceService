package com.littlefrog.service;

import com.littlefrog.entity.Coupon;
import com.littlefrog.entity.Loggers;
import com.littlefrog.respository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author DW
 */
@Service("CouponService")
@EnableScheduling
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public ArrayList<Coupon> getAllCoupon(Integer userID) {
        return couponRepository.findAllCoupon(userID);
    }

    public Coupon addCoupon(Integer userID, double amount, int code, int requirement) {
        Coupon c;
        try {
            if (requirement == 2) {
                c = couponRepository.save(new Coupon(userID, amount, code));
            } else if (couponRepository.findCouponByActivity(userID, code).isPresent()) {
                return null;
            } else {
                c = couponRepository.save(new Coupon(userID, amount, code));
            }
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    public Coupon getCouponInfo(Integer couponID) {
        Optional<Coupon> o = couponRepository.findOneCoupon(couponID);
        return o.orElse(null);
    }

    public boolean useCoupon(Integer couponID) {
        Optional<Coupon> o = couponRepository.findOneCoupon(couponID);
        if (o.isPresent() && o.get().isValid()) {
            couponRepository.deleteCoupon(couponID);
            return true;
        }
        return false;
    }

    public Optional<Coupon> findCouponByActivity(int userID, int code){
        return couponRepository.findCouponByActivity(userID,code);
    }

    public void changLastTime(Integer newTime) {
        Coupon.setLastTime(newTime);
    }

    public int findLastTime() {
        return Coupon.getLastTime();
    }

    /**
     * 按时清理工具
     */
    @Scheduled(cron = "5 0 0 * * ? ")
    public void delete() {
        couponRepository.deleteCoupons();
        Loggers.getLogger().info("Delete  coupon");
    }
}
