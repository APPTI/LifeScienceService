package com.littlefrog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.littlefrog.common.Category;
import com.littlefrog.common.Response;
import com.littlefrog.entity.*;
import com.littlefrog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private InformService informService;
    @Autowired
    private ActivityService activityService;

    @Value("${appid}")
    private String appid;

    private Response response;
    @GetMapping("api/order/getuserOrder")
    public Response getUserOrder(@RequestHeader String appid, @RequestParam int userId){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        List<Order> orderList =orderService.getAllOrder(userId);
        if(orderList.isEmpty()){
            return genFailResult("没有订单！");
        }else{
            return genSuccessResult(orderList);
        }
    }
    @GetMapping("api/order/getuserLike")
    public Response getUserLike(@RequestHeader String appid, @RequestParam int userId){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        List<Order> orderList =orderService.getAllLike(userId);
        if(orderList.isEmpty()){
            return genFailResult("没有订单！");
        }else{
            return genSuccessResult(orderList);
        }
    }
    @GetMapping("api/order/getAllOrderByCourseId")
    public Response getAllOrderByCourseId(@RequestHeader String appid, @RequestParam int courseId){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        List<Order> userList = orderService.getAllOrderByCourseid(courseId);
        if(userList.isEmpty()){
            return genFailResult("没有订单");
        }else{
            return genSuccessResult(userList);
        }
    }
    @GetMapping("api/order/getAllUsersByCourseId")
    public Response getAllUsersByCourseId(@RequestHeader String appid, @RequestParam int courseId){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        List<Integer> useridList = orderService.getAllUserByCourseid(courseId);
        if(useridList.isEmpty()){
            return genFailResult("没有用户");
        }else{
            List<User> userList = userService.findByIds(useridList);
            if(userList.isEmpty()){
                return genFailResult("没有用户");
            }else{
                return genSuccessResult(userList);
            }
        }
    }
    @PostMapping("api/order/addLike")
    public Response addOrder(@RequestHeader String appid, @RequestParam int courseid,@RequestParam int userid){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        Order order = new Order(courseid,userid,new Date(),false);
        order = orderService.addOrder(order);
        if(order == null){
            return genFailResult("创建失败！");
        }else{
            return genSuccessResult(order);
        }
    }
    @PostMapping("api/order/buy")
    public Response buy(@RequestHeader String appid, @RequestParam int couponId, @RequestParam int courseId, @RequestParam int userId){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        double couponMoney=0;
        if(couponId!=-1){
            Coupon coupon= couponService.getCouponInfo(couponId);
            if(coupon.isValid()){
                couponMoney =coupon.getAmount();
                couponService.useCoupon(couponId);
            }else{
                return genFailResult("优惠券已过期！");
            }
        }
        double wallet = userService.getUserInfo(userId).getBalance();
        double price = courseService.findByID(courseId).getPrice();
        if(wallet>=price-couponMoney){
            try{
                Course course = courseService.findByID(courseId);
                Order order = orderService.addOrder(new Order(courseId,userId,true,new Date()));
                userService.payMoney(userId,wallet-price+couponMoney);
                String massage = "恭喜您已经成功购买课程【"+course.getName()+"】,赶快去学习吧！";
                informService.addInform(userId,massage,Category.ORDER,courseId);
                if(couponId==-1){
                    Activity activity = activityService.findByrequirement(courseId,1);
                    if(activity!=null){
                        Coupon.setLastTime(activity.getCouponExpiry());
                        Coupon coupon = couponService.addCoupon(userId,activity.getCoupon());
                        JSONObject result = new JSONObject();
                        result.put("order",order);
                        result.put("coupon",coupon);
                        return genSuccessResult(result);
                    }else{
                        return genSuccessResult(order);
                    }
                }else {
                    return genSuccessResult(order);
                }
            }catch (Exception e){
                return genFailResult(e.getMessage());
            }
        }else{
            return genFailResult("您的余额不足，请充值后购买"+wallet+" "+price);
        }
    }
    @PostMapping("api/order/deleteOrder")
    public Response deleteOrder(@RequestHeader String appid, @RequestParam int orderId){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        orderService.deleteOrder(orderId);
        if(!orderService.getById(orderId).isPresent()){
            return genSuccessResult();
        }
        else{
            return genFailResult("服务器删除失败");
        }
    }
    @PostMapping("api/order/deleteAll")
    public Response deleteAll(@RequestHeader String appid, @RequestParam int userID ,@RequestParam  boolean hasPay){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        orderService.deleteAllOrder(userID,hasPay);
        if(hasPay){
            if(orderService.getAllOrder(userID).isEmpty()){
                return genSuccessResult();
            }else{
                return genFailResult("删除失败,请稍后再试");
            }
        }else{
            if(orderService.getAllLike(userID).isEmpty()){
                return genSuccessResult();
            }else{
                return genFailResult("删除失败,请稍后再试");
            }
        }
    }
}
