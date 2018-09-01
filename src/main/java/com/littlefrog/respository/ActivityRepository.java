package com.littlefrog.respository;

import com.littlefrog.entity.Activity;
import com.littlefrog.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    @Query(value = "SELECT * from activity a where a.courseid=?1", nativeQuery = true)
    Activity findByCourseId(Integer courseId);

    @Transactional
    @Modifying
    @Query(value = "delete from activity where id = ?1", nativeQuery = true)
    void deleteActivity(Integer id);

    /**
     * 删除过期
     */
    @Transactional
    @Modifying
    @Query(value = "delete from activity where expiry < now()", nativeQuery = true)
    void deleteActivities();

    @Transactional
    @Modifying
    @Query(value = "update activity set title=?2,cover_url=?3,content=?4,coupon=?5,coupon_expory=?6.expiry=?7 where id=?1", nativeQuery = true)
    void updateActivity(int id, String title,String cover_url,String url,int coupon,Date coupon_expory,Date expiry);
}
