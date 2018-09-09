package com.littlefrog.service;

import com.littlefrog.entity.ActivityRecord;
import com.littlefrog.respository.ActivityRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ActivityRecordService")
public class ActivityRecordService {
    @Autowired
    private ActivityRecordRepository activityRecordRepository;

    public ActivityRecord getActivityRecord(int userID,int activityID){
        return activityRecordRepository.getActivityRecordByUserID(userID,activityID);
    }

    public ActivityRecord addActivityRecord(ActivityRecord activityRecord){
        return activityRecordRepository.save(activityRecord);
    }
}
