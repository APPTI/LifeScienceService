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

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * from user",nativeQuery = true)
    public List<User> findall();

    @Query(value = "SELECT * FROM user a  where a.id = ?1", nativeQuery = true)
    public User FindById(Integer id);

}
