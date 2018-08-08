package com.littlefrog.service;

import com.littlefrog.entity.User;
import com.littlefrog.respository.UserRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("UserService")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private Store store = new Store();

    public User addUser(String name, String openID){
        User user=userRepository.save(new User(name,openID));
        store.AddUser(user);
        return user;
    }

    public List<User> getAllUser(){
        List<User> u= userRepository.findall();
        return u;
    }

    public User getUserInfo(int id){
        User user = store.getById(id);
        if(user == null) {
            user = userRepository.FindById(id);
            if (user == null){
                return null;
            } else {
                store.AddUser(user);
            }
        }
        return user;
    }

    public User login(String name,String openID){
        User user = userRepository.FindByopenID(openID);
        if(user == null){
            user = userRepository.save(new User(name,openID));
            store.AddUser(user);
            return user;
        }else{
            if(user.getName() == name) {
                userRepository.SetLoginTime(user.getId(),new Date());
                user = userRepository.FindById(user.getId());
                store.UpdateUserInfo(user.getId(),user);
                return user;
            }
            else{
                userRepository.SetName(user.getId(),name);
                userRepository.SetLoginTime(user.getId(),new Date());
                user = userRepository.FindById(user.getId());
                System.out.print(user.toString());
                store.UpdateUserInfo(user.getId(),user);
                return user;
            }
        }
    }
    public User setUserInfo(int id, int gender, String name, String phoneNum){
        userRepository.UpdateInfo(id,gender,name,phoneNum);
        User user = userRepository.FindById(id);
        store.UpdateUserInfo(user.getId(),user);
        return user;
    }

    public User recharge(int id, double money, double balance){
        userRepository.SetBalance(id,balance+money);
        User user = userRepository.FindById(id);
        store.UpdateUserInfo(user.getId(),user);
        return user;
    }

    public User payMoney(int id,double money){
        userRepository.SetBalance(id,money);
        User user = userRepository.FindById(id);
        store.UpdateUserInfo(user.getId(),user);
        return user;
    }
}
