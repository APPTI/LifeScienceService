package com.littlefrog.controller;

import com.littlefrog.common.Response;
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
    public Response addUser(@RequestParam String name, @RequestParam String openId){
       User user = userService.login(name,openId);
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
}
