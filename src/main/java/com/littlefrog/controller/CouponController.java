package com.littlefrog.controller;

import com.littlefrog.common.Response;
import com.littlefrog.entity.Coupon;
import com.littlefrog.service.CouponService;
import com.littlefrog.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
    @Autowired
    private InformService informService;

    @GetMapping("/index")
    public Response index(@RequestParam Integer userID) {
        ArrayList<Coupon> arrayList = couponService.getAllCoupon(userID);
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
    public Response give(@RequestParam Integer userID, @RequestParam double amount) {
        couponService.addCoupon(userID, amount);
        return genSuccessResult();
    }

    @PostMapping("/use")
    public Response use(@RequestParam Integer couponID) {
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

    @GetMapping("/findLastTime")
    public Response giveTime() {
        return genSuccessResult("有效期： " + couponService.findLastTime() + " 天");
    }
}

