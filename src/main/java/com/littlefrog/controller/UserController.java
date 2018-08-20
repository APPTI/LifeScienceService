package com.littlefrog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.littlefrog.common.Response;
import com.littlefrog.common.ResultCode;
import com.littlefrog.entity.Order;
import com.littlefrog.entity.User;
import com.littlefrog.respository.UserRepository;
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
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OrderService orderService;
    @Value("${appid}")
    private String appid;

    @GetMapping("user/index")
    public Response index(@RequestHeader String appid){
        if(appid!=this.appid){
            return genFailResult("错误的appid");
        }
        List<User> u = userService.getAllUser();
        if(u!=null){
            return genSuccessResult(u);
        }else{
            return genFailResult("没有用户");
        }
    }
    @PostMapping("user/login")
    public Response addUser(@RequestHeader String appid, @RequestParam String code){
        if(appid!=this.appid){
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
    @GetMapping("/user/info")
    public Response indexforID(@RequestHeader String appid, @RequestParam Integer id){
        if(appid!=this.appid){
            return genFailResult("错误的appid");
        }
        User user = userService.getUserInfo(id);
        if(user != null){
            return genSuccessResult(user);
        }else{
            return genFailResult("该用户不存在");
        }
    }

    @PostMapping("user/updateInfo")
    public Response updateInfo(@RequestHeader String appid, @RequestParam int userId,@RequestParam int gender,@RequestParam String name, @RequestParam String phoneNum){
        if(appid!=this.appid){
            return genFailResult("错误的appid");
        }
        User user=userService.setUserInfo(userId,gender,name,phoneNum);
        if(user ==null){
            return genFailResult("更新失败，没有该用户！");
        }else{
            return genSuccessResult(user);
        }
    }

    @RequestMapping("user/recharge/getresult")
    @ResponseBody
    public HttpServletResponse getrechargeResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DataInputStream in;
        String wxNotifyXml = "";
        try {
            in = new DataInputStream(request.getInputStream());
            byte[] dataOrigin = new byte[request.getContentLength()];
            in.readFully(dataOrigin); // 根据长度，将消息实体的内容读入字节数组dataOrigin中
            if(null != in) in.close(); // 关闭数据流
            wxNotifyXml = new String(dataOrigin); // 从字节数组中得到表示实体的字符串
            JSONObject result=JSONObject.parseObject(wxNotifyXml);
            if(result.getString("return_code")=="FAIL"){
                response.encodeURL(setXml("SUCCESS",""));
                return response;
            }else if(result.getString("result_code")=="FAIL"){
                response.encodeURL(setXml("SUCCESS",""));
                return response;
            }else{
                int orderId=result.getInteger("out_trade_no");
                double amount = result.getDouble("total_fee");
                Optional<Order> order = orderService.getById(orderId);
                if(order.isPresent()){
                    if(order.get().isIs_recharge()){
                        response.encodeURL(setXml("SUCCESS",""));
                        return response;
                    }else {
                        int userId=order.get().getUserid();
                        User user=userService.AddRecharge(userId,amount,userService.getUserInfo(userId).getBalance());
                        orderService.setIsRecharge(orderId,true);
                        if(user!=null){
                            response.encodeURL(setXml("SUCCESS",""));
                            return response;
                        }else{
                            response.encodeURL(setXml("FAIL","找不到用户"));
                            return response;
                        }
                    }
                }else{
                    response.encodeURL(setXml("FAIL","找不到订单号"));
                    return response;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.encodeURL(setXml("FAIL",e.getLocalizedMessage()));
            return response;
        }
    }
    //通过xml 发给微信消息
    public static String setXml(String return_code, String return_msg) {

        SortedMap<String, String> parameters = new TreeMap<String, String>();

        parameters.put("return_code", return_code);

        parameters.put("return_msg", return_msg);

        return "<xml><return_code><![CDATA[" + return_code + "]]>" +

                "</return_code><return_msg><![CDATA[" + return_msg + "]]></return_msg></xml>";
    }

    @PostMapping("user/recharge")
    public Response recharge(@RequestHeader String appid, @RequestParam int amount,@RequestParam Integer id) throws ParseException {
        if(appid!=this.appid){
            return genFailResult("错误的appid");
        }
        User user = userService.getUserInfo(id);
        if(user==null){
            return genFailResult("该用户不存在!");
        }else{
            Order order =orderService.addOrder(new Order(user.getId()));
            JSONObject response = userService.recharge(user.getOpenid(),order.getId(),amount,request);
            if(response.getString("return_code")=="FAIL"){
                return genFailResult(response.getString("return_msg"));
            }else{
                JSONObject successResponse = JSONObject.parseObject(response.getString("return_msg"));
                if(successResponse.containsKey("err_code")){
                    return genFailResult(successResponse.getString("err_code_des"));
                }else{
                    String prepayId=successResponse.getString("prepay_id");
                    Map successParams=new HashMap<String,String>();
                    successParams.put("appId",appid);
                    Date now = new Date();
                    SimpleDateFormat simFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                    Date start = simFormat.parse("1970.01.01 00:00:00");
                    String timeStamp = Long.toString((now.getTime()-start.getTime())/1000);
                    successParams.put("timeStamp",timeStamp);
                    successParams.put("nonceStr",userService.RandomString(new Random(),16));
                    successParams.put("package",prepayId);
                    successParams.put("signType","MD5");
                    successParams.put("sign",userService.Md5Sign(successParams));
                    return genSuccessResult(successParams);
                }
            }
        }
    }
}
