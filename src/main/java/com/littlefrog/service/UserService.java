package com.littlefrog.service;

import com.littlefrog.entity.User;
import com.littlefrog.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("UserService")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String AddUser(String name, int age){
        userRepository.save(new User(name,age));
        return "添加成功";
    }

    public String Index(){
        List<User> u= userRepository.findall();
        return u.toString();
    }

    public String Index(int id){
        if(userRepository.FindById(id).toString()==null)
            return "null";
        else return userRepository.FindById(id).toString();
    }
}
