package com.littlefrog.store;

import com.littlefrog.entity.Course;
import com.littlefrog.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

public  class Store {
    private final int MAX_SIZE = 100;
    private static Map<Integer,User> userStore = new HashMap<Integer, User>();
    private static Map<Integer, ArrayList<Course>> courseStore = new HashMap<Integer, ArrayList<Course>>();

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

    public List<Course> getCourseList(int id,int index,int offset){
        if (courseStore.containsKey(id)){
            return courseStore.get(id).subList(index,Math.min(index+offset-1,courseStore.get(id).size()));
        }else {
            return null;
        }
    }

    public void addCourseList(int id,List<Course> courseList){
        if (courseStore.size()>MAX_SIZE){
            courseStore.clear();
        }
        courseStore.put(id,(ArrayList)courseList);
    }
}
