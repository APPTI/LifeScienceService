package com.littlefrog.respository;

import com.littlefrog.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(value = "select * from userorder where userid = ?1 AND has_pay =true ",nativeQuery = true)
    List<Order> getAllorder(Integer userId);

    @Query(value = "select * from userorder where userid = ?1 AND has_pay=false",nativeQuery = true)
    List<Order> getAllLike(Integer userId);

    @Query(value = "select * from userorder where courseid = ?1 AND has_pay=true",nativeQuery = true)
    List<Order> getAllOrderByCourseid(Integer courseid);

    @Query(value = "select userid from userorder where courseid = ?1   AND has_pay = true",nativeQuery = true)
    List<Integer> getAlluserIdByCourseid(Integer courseid);


    @Transactional
    @Modifying
    @Query(value = "delete * from userorder where id = ?1",nativeQuery = true)
    public void deleteOrder(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete * from userorder where userid = ?1 AND hasPay =?2 ",nativeQuery = true)
    public void deleteAllOrder(Integer userId,boolean hasPay);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET is_recharge =  ?2 where id = ?1",nativeQuery = true)
    public void SetIsRecharge(Integer Id,boolean isRecharge);

}
