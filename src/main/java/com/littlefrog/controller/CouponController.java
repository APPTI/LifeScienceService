package com.littlefrog.controller;

import com.littlefrog.common.Category;
import com.littlefrog.common.Response;
import com.littlefrog.entity.Coupon;
import com.littlefrog.service.CouponService;
import com.littlefrog.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

/**
 * @author DW
 */
@RequestMapping("api/coupon")
@CrossOrigin
@RestController
public class CouponController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private InformService informService;
    @Value("${appID}")
    private String appID;

    @GetMapping("/index")
    public Response index(@RequestHeader String appID, @RequestParam Integer userID) {
        if (!appID.equals(appID)) {
            return genFailResult("错误的appID");
        }
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
    public Response give(@RequestHeader String appID, @RequestParam Integer userID, @RequestParam double amount) {
        if (!appID.equals(appID)) {
            return genFailResult("错误的appID");
        }
        Coupon c;
        if ((c = couponService.addCoupon(userID, amount)) != null) {
            if (informService.addInform(userID, "您获得了一张新的优惠券！", Category.COUPON, c.getCouponID())) {
                return genSuccessResult();
            } else {
                return genFailResult("获取优惠券成功，但是添加通知失败");
            }
        } else {
            return genFailResult("优惠券添加失败");
        }
    }

    @GetMapping("/info")
    public Response indexForID(@RequestHeader String appID, @RequestParam Integer couponID) {
        if (!appID.equals(appID)) {
            return genFailResult("错误的appID");
        }
        Coupon c = couponService.getCouponInfo(couponID);
        if (c != null) {
            return genSuccessResult(c);
        } else {
            return genFailResult("没有优惠券信息");
        }
    }

    @PostMapping("/changeLastTime")
    public Response changeTime(@RequestHeader String appID, @RequestParam Integer newTime) {
        if (!appID.equals(appID)) {
            return genFailResult("错误的appID");
        }
        couponService.changLastTime(newTime);
        return genSuccessResult();
    }

    @GetMapping("/findLastTime")
    public Response giveTime(@RequestHeader String appID) {
        if (!appID.equals(appID)) {
            return genFailResult("错误的appID");
        }
        return genSuccessResult("有效期： " + couponService.findLastTime() + " 天");
    }
}

