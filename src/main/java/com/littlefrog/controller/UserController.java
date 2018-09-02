package com.littlefrog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.littlefrog.common.Response;
import com.littlefrog.common.ResultCode;
import com.littlefrog.entity.Activity;
import com.littlefrog.entity.Coupon;
import com.littlefrog.entity.Order;
import com.littlefrog.entity.User;
import com.littlefrog.respository.UserRepository;
import com.littlefrog.service.ActivityService;
import com.littlefrog.service.CouponService;
import com.littlefrog.service.OrderService;
import com.littlefrog.service.UserService;
import com.sun.org.apache.bcel.internal.classfile.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private CouponService couponService;

    @Value("${appid}")
    private String appid;

    @GetMapping("api/user/index")
    public Response index(@RequestHeader String appid){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        List<User> u = userService.getAllUser();
        if(u!=null){
            return genSuccessResult(u);
        }else{
            return genFailResult("没有用户");
        }
    }
    @PostMapping("api/user/login")
    public Response addUser(@RequestHeader String appid, @RequestParam String code){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
       Object user = userService.login(code);
       if(user.getClass() != User.class){
           JSONObject res = JSONObject.parseObject(user.toString());
           String massage = res.getString("errmsg");
           return genFailResult(massage);
       }
       if(user != null){
           return genSuccessResult(user);
       }else{
           return genFailResult("登陆失败，请稍后再试");
       }
    }
    @GetMapping("api/user/info")
    public Response indexforID(@RequestHeader String appid, @RequestParam Integer id){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        User user = userService.getUserInfo(id);
        if(user != null){
            return genSuccessResult(user);
        }else{
            return genFailResult("该用户不存在");
        }
    }

    @PostMapping("api/user/updateInfo")
    public Response updateInfo(@RequestHeader String appid, @RequestParam int userId,@RequestParam int gender,@RequestParam String name, @RequestParam String phoneNum){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        User user=userService.setUserInfo(userId,gender,name,phoneNum);
        if(user ==null){
            return genFailResult("更新失败，没有该用户！");
        }else{
            return genSuccessResult(user);
        }
    }

    @PostMapping("api/user/rechargeResult")
    public Response rechargeResult(@RequestHeader String appid,@RequestParam int userid, @RequestParam int ammount,@RequestParam boolean isSuccess){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        if(!isSuccess){
            return genSuccessResult();
        }else{
            Activity activity = activityService.findByrequirementAndAmmount(ammount,2);
            if(activity==null){
                return genSuccessResult();
            }else{
                try {
                    Coupon.setLastTime(activity.getCouponExpiry());
                    Coupon coupon = couponService.addCoupon(userid, activity.getCoupon());
                    return genSuccessResult("恭喜您获得"+activity.getCoupon()+"元优惠券");
                }catch (Exception e){
                    return genFailResult(e.getMessage());
                }
            }
        }
    }
}
