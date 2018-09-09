package com.littlefrog.respository;

import com.littlefrog.entity.ActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRecordRepository extends JpaRepository<ActivityRecord, Integer> {
    @Query(value = "select * from activity_record a where a.user_id = ?1 and a.activity_id = ?2",nativeQuery = true)
    public ActivityRecord getActivityRecordByUserID(int userID,int activityID);
}
