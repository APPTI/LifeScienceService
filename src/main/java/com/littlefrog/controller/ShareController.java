package com.littlefrog.controller;

import com.littlefrog.common.Response;
import com.littlefrog.common.ResultGenerator;
import com.littlefrog.entity.Activity;
import com.littlefrog.entity.Coupon;
import com.littlefrog.service.ActivityService;
import com.littlefrog.service.CouponService;
import com.littlefrog.service.CourseService;
import com.littlefrog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ShareController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CouponService couponService;

    @Value("${appid}")
    private String appid;

    @PostMapping("api/share")
    public Response AddShare(@RequestHeader String appid, @RequestParam int userId, @RequestParam int courseId){
        if(!this.appid.equals(appid)){
            return ResultGenerator.genFailResult("错误的appid");
        }
        Activity activity = activityService.findByrequirement(courseId,0);
        if(activity==null){
            return ResultGenerator.genSuccessResult("成功");
        }else{
            try {
                Coupon.setLastTime(activity.getCouponExpiry());
                Coupon coupon = couponService.addCoupon(userId,activity.getCoupon());
                String msg = "恭喜您获得"+activity.getCoupon()+"元优惠券";
                return ResultGenerator.genSuccessResult(msg);
            }catch (Exception e){
                return ResultGenerator.genFailResult(e.getMessage());
            }
        }
    }
}
