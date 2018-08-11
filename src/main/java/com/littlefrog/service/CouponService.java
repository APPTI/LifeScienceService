package com.littlefrog.service;

import com.littlefrog.entity.Coupon;
import com.littlefrog.respository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    public void addCoupon(Integer userID, double amount) {
        couponRepository.save(new Coupon(userID, amount));
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

    public void changLastTime(Integer newTime) {
        Coupon.setLastTime(newTime);
    }


    /**
     * 按时清理工具
     */
    @Scheduled(cron = "50 16 13 * * ? ")
    public void delete() {
        couponRepository.deleteCoupons();
        try {
            Date data = new Date();
            FileWriter f = new FileWriter(new File("a.txt"), true);
            f.write("Delete  :");
            f.write(data.toString());
            f.write("\n");
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}