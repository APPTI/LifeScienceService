package com.littlefrog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.littlefrog.common.Category;
import com.littlefrog.common.Response;
import com.littlefrog.common.ResultGenerator;
import com.littlefrog.entity.*;
import com.littlefrog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private ActivityRecordService activityRecordService;

    @Value("${appID}")
    private String appID;

    private Response response;

    @GetMapping("api/order/getUserOrder")
    public Response getUserOrder(@RequestHeader String appID, @RequestParam int userID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        List<Order> orderList = orderService.getAllOrder(userID);
        if (orderList.isEmpty()) {
            return genFailResult("没有订单！");
        } else {
            return genSuccessResult(orderList);
        }
    }

    @GetMapping("api/order/getUserLike")
    public Response getUserLike(@RequestHeader String appID, @RequestParam int userID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        List<Order> orderList = orderService.getAllLike(userID);
        if (orderList.isEmpty()) {
            return genFailResult("没有订单！");
        } else {
            return genSuccessResult(orderList);
        }
    }

    @GetMapping("api/order/getAllOrderByCourseID")
    public Response getAllOrderByCourseID(@RequestHeader String appID, @RequestParam int courseID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        List<Order> userList = orderService.getAllOrderByCourseid(courseID);
        if (userList.isEmpty()) {
            return genFailResult("没有订单");
        } else {
            return genSuccessResult(userList);
        }
    }

    @GetMapping("api/order/getAllUsersByCourseID")
    public Response getAllUsersByCourseID(@RequestHeader String appID, @RequestParam int courseID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        List<Integer> useridList = orderService.getAllUserByCourseid(courseID);
        if (useridList.isEmpty()) {
            return genFailResult("没有用户");
        } else {
            List<User> userList = userService.findByIds(useridList);
            if (userList.isEmpty()) {
                return genFailResult("没有用户");
            } else {
                return genSuccessResult(userList);
            }
        }
    }

    @PostMapping("api/order/addLike")
    public Response addOrder(@RequestHeader String appID, @RequestParam int courseID, @RequestParam int userID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        Order order = new Order(courseID, userID, new Date(), false);
        order = orderService.addOrder(order);
        if (order == null) {
            return genFailResult("创建失败！");
        } else {
            return genSuccessResult(order);
        }
    }

    @PostMapping("api/order/buy")
    public Response buy(@RequestHeader String appID, @RequestParam int couponID, @RequestParam int courseID, @RequestParam int userID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }

        double couponMoney = 0;
        if (couponID != -1) {
            Coupon coupon = couponService.getCouponInfo(couponID);
            if (coupon.isValid()) {
                couponMoney = coupon.getAmount();
            } else {
                return genFailResult("优惠券已过期！");
            }
        }
        double wallet = userService.getUserInfo(userID).getBalance();
        double price = courseService.findByID(courseID).getPrice();
        if (wallet >= price - couponMoney) {
            try {
                String message;
                Course course = courseService.findByID(courseID);
                Order order = orderService.addOrder(new Order(courseID, userID, true, new Date()));
                if (order == null) {
                    message = "您已购买过啦";
                    return genFailResult(message);
                } else {
                    if(couponID!=-1) {
                        couponService.useCoupon(couponID);
                    }
                    userService.payMoney(userID, wallet - price + couponMoney);
                    message = "恭喜您已经成功购买课程【" + course.getName() + "】,赶快去学习吧！";
                    informService.addInform(userID, message, Category.ORDER, courseID);
                    if (couponID == -1) {
                        Activity activity = activityService.findByrequirement(courseID, 1);
                        if (activity != null) {
                            ActivityRecord activityRecord = activityRecordService.getActivityRecord(userID, activity.getActivityID());
                            if(activityRecord!=null){
                                return ResultGenerator.genSuccessResult("成功");
                            }
                            activityRecordService.addActivityRecord(new ActivityRecord(userID,activity.getActivityID()));
                            Coupon.setLastTime(activity.getCouponExpiry());
                            Coupon mycoupon = couponService.addCoupon(userID, activity.getCoupon(), activity.getActivityID(), activity.getRequirement());
                            informService.addInform(userID,"恭喜您成功获得"+mycoupon.getAmount()+"元优惠券，赶快去使用吧！",Category.SYSTEM,null);
                            JSONObject result = new JSONObject();
                            result.put("order", order);
                            result.put("coupon", mycoupon);
                            return genSuccessResult(result);
                        } else {
                            return genSuccessResult(order);
                        }
                    } else {
                        return genSuccessResult(order);
                    }
                }
            } catch (Exception e) {
                return genFailResult(e.getMessage());
            }
        } else {
            return genFailResult("您的余额不足，请充值后购买" + wallet + " " + price);
        }
    }

    @PostMapping("api/order/deleteOrder")
    public Response deleteOrder(@RequestHeader String appID, @RequestParam int orderID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        orderService.deleteOrder(orderID);
        if (!orderService.getById(orderID).isPresent()) {
            return genSuccessResult();
        } else {
            return genFailResult("服务器删除失败");
        }
    }

    @PostMapping("api/order/deleteAll")
    public Response deleteAll(@RequestHeader String appID, @RequestParam int userID, @RequestParam boolean hasPay) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        orderService.deleteAllOrder(userID, hasPay);
        if (hasPay) {
            if (orderService.getAllOrder(userID).isEmpty()) {
                return genSuccessResult();
            } else {
                return genFailResult("删除失败,请稍后再试");
            }
        } else {
            if (orderService.getAllLike(userID).isEmpty()) {
                return genSuccessResult();
            } else {
                return genFailResult("删除失败,请稍后再试");
            }
        }
    }
}
