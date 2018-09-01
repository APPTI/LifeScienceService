package com.littlefrog.service;

import com.littlefrog.entity.Activity;
import com.littlefrog.respository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("ActivityService")
@EnableScheduling
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;


    public Activity AddActivity(String title, String cover_url,String url, Boolean isHasCoupon, int coupon,int coupon_expiry,Date expiry,int requirement,int courseId,int ammount){
        return activityRepository.save(new Activity(title,cover_url,url,isHasCoupon,coupon,coupon_expiry,expiry,requirement,courseId,ammount));
    }

    public boolean DeleteActivity(int id){
        try {
            activityRepository.deleteActivity(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Activity findByrequirement(int courseId, int requirement){
        return activityRepository.findActivityByCourseIdAndRequirement(courseId,requirement);
    }

    public Activity findByrequirementAndAmmount(int ammount, int requirement){
        return activityRepository.findActivityByAmmountAndRequirement(ammount,requirement);
    }

    public Optional<Activity> findById(int id){
        return activityRepository.findById(id);
    }

    public List<Activity> findAllActivity(){
        return activityRepository.findAll();
    }

    public boolean Update(int id,String title,String cover_url, String url, int coupon,Date coupon_expiry,Date expiry){
        try{
            activityRepository.updateActivity(id,title,cover_url,url,coupon,coupon_expiry,expiry);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 按时清理工具
     */
    @Scheduled(cron = "30 00 00 * * ? ")
    public void delete() {
        activityRepository.deleteActivities();
        try {
            Date data = new Date();
            FileWriter f = new FileWriter(new File("activity.txt"), true);
            f.write("Delete  :");
            f.write(data.toString());
            f.write("\n");
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
