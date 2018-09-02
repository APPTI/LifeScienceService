package com.littlefrog.respository;

import com.littlefrog.entity.User;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * from user",nativeQuery = true)
    public List<User> findall();

    @Query(value = "SELECT * FROM user a  where a.user_id = ?1", nativeQuery = true)
    public User FindById(Integer id);

    @Query(value = "select * from user where open_id = ?1", nativeQuery = true)
    public User FindByopenID(String openId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET name = ?2 where user_id = ?1",nativeQuery = true)
    public void SetName(Integer Id, String newName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET balance = ?2 where user_id = ?1",nativeQuery = true)
    public void SetBalance(Integer Id, double balance);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET phone_num = ?2, phone_valid=true where user_id = ?1",nativeQuery = true)
    public void SetPhoneNum(Integer Id, String phoneNum);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET gender = ?2 where user_id = ?1",nativeQuery = true)
    public void SetGender(Integer Id, int Gender);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET open_id = ?2 where user_id = ?1",nativeQuery = true)
    public void SetOpenId(Integer Id, String openId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET last_login_time = ?2 where user_id = ?1",nativeQuery = true)
    public void SetLoginTime(Integer Id, Date time);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET gender =  ?2, name = ?3, phone_num = ?4,phone_valid=true where user_id = ?1",nativeQuery = true)
    public void UpdateInfo(Integer Id,int gender,String name, String phoneNum);

    @Transactional
    @Modifying
    @Query(value = "delete from user where user_id = ?1",nativeQuery = true)
    public void DeleteUser(Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET session_id =  ?2 where user_id = ?1",nativeQuery = true)
    public void UpdateSessionId(Integer Id,String sessionId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user SET union_id =  ?2 where user_id = ?1",nativeQuery = true)
    public void UpdateUnionId(Integer Id,String unionId);

    @Query(value = "select LAST_INSERT_ID()",nativeQuery = true)
    public Integer GetLastInsertId();

}
