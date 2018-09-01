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
    public Response AddActivity(@RequestHeader String appid, @RequestParam String title,@RequestParam String cover_url,@RequestParam String url, @RequestParam Boolean isHasCoupon ,@RequestParam int coupon, @RequestParam Date coupon_expiry,@RequestParam Date expiry,@RequestParam int requirement,@RequestParam int courseId,@RequestParam int ammount){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        try{
            Activity activity = activityService.AddActivity(title,cover_url,url,isHasCoupon,coupon,coupon_expiry,expiry,requirement,courseId,ammount);
            return genSuccessResult(activity);
        }catch (Exception e){
            return genFailResult(e.getMessage());
        }
    }

    @PostMapping("api/activity/updateActivity")
    public Response updateActivity(@RequestHeader String appid, @RequestParam int id,@RequestParam String title,@RequestParam String cover_url,@RequestParam String url,@RequestParam int coupon, @RequestParam Date coupon_expiry,@RequestParam Date expiry){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        try{
            Boolean result = activityService.Update(id,title,cover_url,url,coupon,coupon_expiry,expiry);
            if(result){
                Optional<Activity> activity = activityService.findById(id);
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
    public Response getActivity(@RequestHeader String appid,@RequestParam int id){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        try{
            Optional<Activity> activity = activityService.findById(id);
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
    public Response deleteActivity(@RequestHeader String appid,@RequestParam int id){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        try{
            Boolean result = activityService.DeleteActivity(id);
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
