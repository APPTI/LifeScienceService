package com.littlefrog.store;

import com.littlefrog.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public  class Store {
    private final int MAX_SIZE = 100;
    private static Map<Integer,User> userStore = new HashMap<Integer, User>();

    public User getById(int Id){
        if(userStore.containsKey(Id)){
            return userStore.get(Id);
        }else{
            return null;
        }
    }
    public void AddUser(User user){
        if(userStore.containsValue(user))
            return;
        else{
            if(userStore.size()>MAX_SIZE){
                userStore.clear();
                userStore.put(user.getId(),user);
            }
        }
    }
    public void UpdateUserInfo(int id,User user){
        if(userStore.containsKey(id)){
            userStore.replace(id,user);
        }else{
            this.AddUser(user);
        }
    }
}
