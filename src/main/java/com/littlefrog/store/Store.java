package com.littlefrog.store;

import com.littlefrog.entity.Course;
import com.littlefrog.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public  class Store {
    private final int MAX_SIZE = 100;
    private static Map<Integer,User> userStore = new HashMap<Integer, User>();
    private static Map<Integer, Course> courseStore = new HashMap<Integer, Course>();

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

    public Course getCourseById(int id){
        if (courseStore.containsKey(id)){
            return courseStore.get(id);
        }else {
            return null;
        }
    }

    public void addCourse(Course course){
        if (courseStore.containsValue(course)){
            return;
        }
        else {
            if (courseStore.size()>MAX_SIZE){
                courseStore.clear();
            }
            courseStore.put(course.getId(),course);
        }
    }
}
