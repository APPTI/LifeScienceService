package com.littlefrog.service;

import com.littlefrog.entity.Order;
import com.littlefrog.respository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.littlefrog.respository.StudyRecordRepository;
import java.util.List;
import java.util.Optional;

@Service("OrderService")
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StudyRecordRepository studyRecordRepository;

    public Optional<Order> getById(int orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        if(!order.isPresent()){
            return null;
        }
        else return order;
    }

    public List<Order> getAllOrder(int userId){
        List<Order> orderList = orderRepository.getAllorder(userId);
        return orderList;
    }

    public List<Order> getAllLike(int userId){
        List<Order> orderList = orderRepository.getAllLike(userId);
        return orderList;
    }

    public Order addOrder(Order order){
        Order result = orderRepository.save(order);
        if (order.getHas_pay()) {
            studyRecordRepository.addProgress(order.getUserid(), order.getCourseid());
        }
        return result;
    }
    public List<Order> getAllOrderByCourseid(int courseId){
        List<Order> orderList = orderRepository.getAllOrderByCourseid(courseId);
        return orderList;
    }

    public List<Integer> getAllUserByCourseid(int courseId){
        List<Integer> userList = orderRepository.getAlluserIdByCourseid(courseId);
        return userList;
    }

    public void deleteOrder(int orderId){
        orderRepository.deleteOrder(orderId);
    }

    public void deleteAllOrder(int userId,boolean hasPay){
        orderRepository.deleteAllOrder(userId,hasPay);
    }

    public void setIsRecharge(int orderId,boolean isRecharge){
        orderRepository.SetIsRecharge(orderId,isRecharge);
    }

}
