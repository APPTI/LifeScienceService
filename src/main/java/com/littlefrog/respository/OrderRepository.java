package com.littlefrog.respository;

import com.littlefrog.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(value = "select * from user_order where user_id = ?1 AND has_pay =true ",nativeQuery = true)
    List<Order> getAllorder(Integer userId);

    @Query(value = "select * from user_order where user_id = ?1 AND has_pay=false",nativeQuery = true)
    List<Order> getAllLike(Integer userId);

    @Query(value = "select * from user_order where course_id = ?1 AND has_pay=true",nativeQuery = true)
    List<Order> getAllOrderByCourseid(Integer courseid);

    @Query(value = "select user_id from user_order where course_id = ?1   AND has_pay = true",nativeQuery = true)
    List<Integer> getAlluserIdByCourseid(Integer courseid);


    @Transactional
    @Modifying
    @Query(value = "delete * from user_order where order_id = ?1",nativeQuery = true)
    public void deleteOrder(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete * from user_order where user_id = ?1 AND has_pay =?2 ",nativeQuery = true)
    public void deleteAllOrder(Integer userId,boolean hasPay);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user_order SET is_recharge =  ?2 where order_id = ?1",nativeQuery = true)
    public void SetIsRecharge(Integer Id,boolean isRecharge);

}
