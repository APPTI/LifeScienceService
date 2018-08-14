package com.littlefrog.respository;

import com.littlefrog.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Query(value = "select * from userorder where userid = ?1 AND has_pay =true ",nativeQuery = true)
    List<Order> getAllorder(Integer userId);

    @Query(value = "select * from userorder where userid = ?1 AND has_pay=false",nativeQuery = true)
    List<Order> getAllLike(Integer userId);

    @Query(value = "select * from userorder where courseid = ?1 AND has_pay=true ",nativeQuery = true)
    List<Order> getAllOrderByCourseid(Integer courseid);

    @Query(value = "select userid from userorder where courseid = ?1   AND has_pay = true",nativeQuery = true)
    List<Integer> getAlluserIdByCourseid(Integer courseid);


}
