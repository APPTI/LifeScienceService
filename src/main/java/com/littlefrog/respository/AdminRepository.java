package com.littlefrog.respository;

import com.littlefrog.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    @Query(value = "select password from admin where admin_name = ?1",nativeQuery = true)
    public String getPasswdByname(String admin_name);
}
