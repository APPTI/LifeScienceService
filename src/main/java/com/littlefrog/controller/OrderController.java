package com.littlefrog.controller;

import com.littlefrog.common.Response;
import com.littlefrog.entity.Coupon;
import com.littlefrog.entity.Order;
import com.littlefrog.entity.User;
import com.littlefrog.service.CouponService;
import com.littlefrog.service.CourseService;
import com.littlefrog.service.OrderService;
import com.littlefrog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CourseService courseService;
    private Response response;
    @GetMapping("order/getuserOrder")
    public Response getUserOrder(@RequestParam int userId){
        List<Order> orderList =orderService.getAllOrder(userId);
        if(orderList.isEmpty()){
            return genFailResult("没有订单！");
        }else{
            return genSuccessResult(orderList);
        }
    }
    @GetMapping("order/getuserLike")
    public Response getUserLike(@RequestParam int userId){
        List<Order> orderList =orderService.getAllLike(userId);
        if(orderList.isEmpty()){
            return genFailResult("没有订单！");
        }else{
            return genSuccessResult(orderList);
        }
    }
    @GetMapping("order/getAllOrderByCourseId")
    public Response getAllOrderByCourseId(@RequestParam int courseId, @RequestParam int term){
        List<Order> userList = orderService.getAllOrderByCourseid(courseId,term);
        if(userList.isEmpty()){
            return genFailResult("没有订单");
        }else{
            return genSuccessResult(userList);
        }
    }
    @GetMapping("order/getAllUsersByCourseId")
    public Response getAllUsersByCourseId(@RequestParam int courseId,@RequestParam int term){
        List<Integer> useridList = orderService.getAllUserByCourseid(courseId,term);
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
    @PostMapping("order/addLike")
    public Response addOrder(@RequestParam int courseId,@RequestParam int userId,@RequestParam int  term){
        Order order = new Order(courseId,term,userId,new Date(),false);
        order = orderService.addOrder(order);
        if(order == null){
            return genFailResult("创建失败！");
        }else{
            return genSuccessResult(order);
        }
    }
    @PostMapping("order/buy")
    public Response buy(@RequestParam int couponId,@RequestParam int courseId,@RequestParam int userId,@RequestParam int term){
        double couponMoney=0;
        if(couponId!=-1){
            Coupon coupon= couponService.getCouponInfo(couponId);
            if(coupon.isValid()){
                couponMoney =coupon.getAmount();
            }else{
                return genFailResult("优惠券已过期！");
            }
        }
        double wallet = userService.getUserInfo(userId).getBalance();
        double price = courseService(courseId).getPrice();
        if(wallet>price-couponMoney){
            userService.payMoney(userId,price-couponMoney);
            return genSuccessResult();
        }else{
            return genFailResult("您的余额不足，请充值后购买");
        }
    }
    @PostMapping("order/deleteOrder")
    public Response deleteOrder(@RequestParam int orderId){
        orderService.deleteOrder(orderId);
        if(orderService.getById(orderId)==null){
            return genSuccessResult();
        }
        else{
            return genFailResult("服务器删除失败");
        }
    }
    @PostMapping("order/deleteAll")
    public Response deleteAll(@RequestParam int userID ,@RequestParam  boolean hasPay){
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
