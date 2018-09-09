package com.littlefrog.controller;

import com.littlefrog.common.Response;
import com.littlefrog.service.AdminService;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.AbstractDecimalMinValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@CrossOrigin
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Value("${appID}")
    private String appID;

    @PostMapping("api/admin/login")
    public Response adminLogin(@RequestHeader String appID, @RequestParam String name, @RequestParam String passwd){
        if(!appID.equals(this.appID)){
            return genFailResult("错误的appid");
        }
        if(adminService.adminLogin(name,passwd)){
            return genSuccessResult("SUCCESS");
        }else{
            return genFailResult("用户名或密码错误");
        }
    }
}
