package com.littlefrog.respository;

import com.littlefrog.entity.User;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * from user",nativeQuery = true)
    public List<User> findall();

    @Query(value = "SELECT * FROM user a  where a.id = ?1", nativeQuery = true)
    public User FindById(Integer id);

    @Query(value = "select * from user where openID = ?1", nativeQuery = true)
    public User FindByopenID(String openId);

    @Modifying
    @Query(value = "UPDATE user SET name = ?2 where id = ?1;select * from user where id = ?1",nativeQuery = true)
    public User SetName(Integer Id, String newName);

    @Modifying
    @Query(value = "UPDATE user SET balance = ?2 where id = ?1;select * from user where id = ?1",nativeQuery = true)
    public User SetBalance(Integer Id, double balance);

    @Modifying
    @Query(value = "UPDATE user SET phoneNum = ?2, phoneValid=true where id = ?1;select * from user where id = ?1",nativeQuery = true)
    public User SetPhoneNum(Integer Id, String phoneNum);

    @Modifying
    @Query(value = "UPDATE user SET gender = ?2 where id = ?1;select * from user where id = ?1",nativeQuery = true)
    public User SetGender(Integer Id, int Gender);

    @Modifying
    @Query(value = "UPDATE user SET openID = ?2 where id = ?1;select * from user where id = ?1",nativeQuery = true)
    public User SetOpenId(Integer Id, String openId);

    @Modifying
    @Query(value = "UPDATE user SET lastLoginTime = ?2 where id = ?1;select * from user where id = ?1",nativeQuery = true)
    public User SetLoginTime(Integer Id, Date time);

    @Modifying
    @Query(value = "UPDATE user SET gender =  ?2, name = ?3, phoneNum = ?4 where id = ?1;select * from user where id = ?1",nativeQuery = true)
    public User UpdateInfo(Integer Id,int gender,String name, String phoneNum);

    @Modifying
    @Query(value = "delete * from user where id = ?1",nativeQuery = true)
    public void DeleteUser(Integer id);

    @Query(value = "select LAST_INSERT_ID();",nativeQuery = true)
    public Integer GetLastInsertId();

}
