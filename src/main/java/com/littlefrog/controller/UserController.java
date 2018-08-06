package com.littlefrog.controller;

import com.littlefrog.entity.User;
import com.littlefrog.respository.UserRepository;
import com.littlefrog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(){
        return userService.Index();
    }
    @PostMapping("/add")
    public String addUser(@RequestParam String name, @RequestParam Integer age){
       return userService.AddUser(name,age);
    }
    @GetMapping("/index/{id}")
    public String indexforID(@PathVariable Integer id){
        return userService.Index(id);
    }
}
