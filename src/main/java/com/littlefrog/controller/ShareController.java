package com.littlefrog.controller;

import com.littlefrog.common.Category;
import com.littlefrog.common.Response;
import com.littlefrog.common.ResultGenerator;
import com.littlefrog.entity.Activity;
import com.littlefrog.entity.ActivityRecord;
import com.littlefrog.entity.Coupon;
import com.littlefrog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class ShareController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private InformService informService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private ActivityRecordService activityRecordService;

    @Value("${appID}")
    private String appID;

    @PostMapping("api/share")
    public Response AddShare(@RequestHeader String appID, @RequestParam int userID, @RequestParam int courseID){
        if(!this.appID.equals(appID)){
            return ResultGenerator.genFailResult("错误的appID");
        }
        Activity activity = activityService.findByrequirement(courseID,0);
        if(activity==null){
            return ResultGenerator.genSuccessResult("成功");
        }else{
            try {
                ActivityRecord activityRecord = activityRecordService.getActivityRecord(userID, activity.getActivityID());
                if(activityRecord!=null){
                    return ResultGenerator.genSuccessResult("成功");
                }
                activityRecordService.addActivityRecord(new ActivityRecord(userID,activity.getActivityID()));
                Coupon.setLastTime(activity.getCouponExpiry());
                couponService.addCoupon(userID,activity.getCoupon(),activity.getActivityID(),activity.getRequirement());
                String msg = "恭喜您获得"+activity.getCoupon()+"元优惠券,快去使用吧";
                informService.addInform(userID,msg,Category.SYSTEM,null);
                return ResultGenerator.genSuccessResult("成功");
            }catch (Exception e){
                return ResultGenerator.genFailResult(e.getMessage());
            }
        }
    }
}
