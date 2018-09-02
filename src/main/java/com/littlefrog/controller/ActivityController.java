package com.littlefrog.controller;

import com.littlefrog.common.Response;
import com.littlefrog.entity.Activity;
import com.littlefrog.service.ActivityService;
import com.littlefrog.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@CrossOrigin
@RestController
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @Autowired
    private OrderService orderService;
    @Value("${appid}")
    private String appid;

    @PostMapping("api/activity/addActivity")
    public Response AddActivity(@RequestHeader String appid, @RequestParam String title,@RequestParam String coverUrl,@RequestParam String url, @RequestParam Boolean isHasCoupon ,@RequestParam int coupon, @RequestParam int couponExpiry,@RequestParam Date expiry,@RequestParam int requirement,@RequestParam int courseID,@RequestParam int amount){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        try{
            Activity activity = activityService.AddActivity(title,coverUrl,url,isHasCoupon,coupon,couponExpiry,expiry,requirement,courseID,amount);
            return genSuccessResult(activity);
        }catch (Exception e){
            return genFailResult(e.getMessage());
        }
    }

    @PostMapping("api/activity/updateActivity")
    public Response updateActivity(@RequestHeader String appid, @RequestParam int ID,@RequestParam String title,@RequestParam String coverUrl,@RequestParam String url,@RequestParam int coupon, @RequestParam Date couponExpiry,@RequestParam Date expiry){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        try{
            Boolean result = activityService.Update(ID,title,coverUrl,url,coupon,couponExpiry,expiry);
            if(result){
                Optional<Activity> activity = activityService.findById(ID);
                if(activity.isPresent()){
                    return genSuccessResult(activity);
                }else{
                    return genFailResult("找不到活动");
                }
            }else{
                return genFailResult("更新失败");
            }
        }catch (Exception e){
            return genFailResult(e.getMessage());
        }
    }


    @GetMapping("api/activity/getAllActivities")
    public Response getAllActivities(@RequestHeader String appid){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        try{
            List<Activity> activities = activityService.findAllActivity();
            return genSuccessResult(activities);
        }catch (Exception e){
            return genFailResult(e.getMessage());
        }
    }

    @GetMapping("api/activity/getActivity")
    public Response getActivity(@RequestHeader String appid,@RequestParam int ID){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        try{
            Optional<Activity> activity = activityService.findById(ID);
            if(activity.isPresent()) {
                return genSuccessResult(activity);
            }else{
                return genFailResult("找不到活动");
            }
        }catch (Exception e){
            return genFailResult(e.getMessage());
        }
    }

    @PostMapping("api/activity/deleteActivity")
    public Response deleteActivity(@RequestHeader String appid,@RequestParam int ID){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        try{
            Boolean result = activityService.DeleteActivity(ID);
            if(result) {
                return genSuccessResult("删除活动成功");
            }else{
                return genFailResult("删除活动失败");
            }
        }catch (Exception e){
            return genFailResult(e.getMessage());
        }
    }
}
