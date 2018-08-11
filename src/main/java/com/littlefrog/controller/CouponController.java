package com.littlefrog.controller;

import com.littlefrog.common.Response;
import com.littlefrog.entity.Coupon;
import com.littlefrog.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

/**
 * @author DW
 */
@RequestMapping("/coupon")
@RestController
public class CouponController {
    @Autowired
    private CouponService couponService;

    @GetMapping("/index")
    public Response index(@RequestParam Integer userID) {
        ArrayList<Coupon> arrayList = couponService.getAllCoupon(userID);
        Calendar now = Calendar.getInstance();
        if (arrayList != null && arrayList.size() != 0) {
            for (Coupon coupon : arrayList) {
                coupon.setValid();
            }
            return genSuccessResult(arrayList);
        } else {
            return genFailResult("没有优惠券");
        }
    }

    @PostMapping("/give")
    public Response send(@RequestParam Integer userID, @RequestParam double amount) {
        couponService.addCoupon(userID, amount);
        return genSuccessResult();
    }

    @PostMapping("/use")
    public Response send(@RequestParam Integer couponID) {
        if (couponService.useCoupon(couponID)) {
            return genSuccessResult();
        } else {
            return genFailResult("使用失败");
        }
    }

    @GetMapping("/info")
    public Response indexForID(@RequestParam Integer couponID) {
        Coupon c = couponService.getCouponInfo(couponID);
        if (c != null) {
            return genSuccessResult(c);
        } else {
            return genFailResult("没有优惠券信息");
        }
    }

    @PostMapping("/changeLastTime")
    public Response changeTime(@RequestParam Integer newTime) {
        couponService.changLastTime(newTime);
        return genSuccessResult();
    }
}

