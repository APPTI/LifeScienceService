package com.littlefrog.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.littlefrog.common.Response;
import com.littlefrog.common.ResultCode;
import com.littlefrog.entity.User;
import com.littlefrog.respository.UserRepository;
import com.littlefrog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private Response response;

    @GetMapping("user/index")
    public Response index(){
        List<User> u = userService.getAllUser();
        if(u!=null){
            return genSuccessResult(u);
        }else{
            return genFailResult("没有用户");
        }
    }
    @PostMapping("user/login")
    public Response addUser(@RequestParam String code){
       Object user = userService.login(code);
       if(user.getClass() != User.class){
           JSONObject res = JSONObject.parseObject(user.toString());
           int errcode = res.getInteger("errcode");
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
    public Response indexforID(@RequestParam Integer id){
        User user = userService.getUserInfo(id);
        if(user != null){
            return genSuccessResult(user);
        }else{
            return genFailResult("该用户不存在");
        }
    }

    @PostMapping("user/recharge")
    public Response recharge(@RequestParam int amount,@RequestParam Integer id){
        User user = userService.getUserInfo(id);
        user = userService.recharge(user.getId(),amount,user.getBalance());
        if(user != null){
            return genSuccessResult(user.getBalance());
        }else{
            return genFailResult("该用户不存在");
        }
    }
}
