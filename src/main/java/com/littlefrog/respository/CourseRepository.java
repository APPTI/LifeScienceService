package com.littlefrog.respository;


import com.littlefrog.common.Tag;
import com.littlefrog.entity.Course;
import com.littlefrog.entity.Course;
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


public interface CourseRepository extends JpaRepository<Course,Integer>{
    @Query (value = "SELECT * from course",nativeQuery = true)
    public List<Course> findAll();


    @Query (value = "SELECT * FROM course a  where a.id = ?1",nativeQuery = true)
    public Course FindById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET name = ?2 where id = ?1",nativeQuery = true)
    public void setName(Integer Id, String newName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET teacher = ?2 where id = ?1",nativeQuery = true)
    public void setTeacherName(Integer Id, String teacherName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET introduction = ?2 where id = ?1",nativeQuery = true)
    public void setIntroduction(Integer Id, int introduction);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET tag = ?2 where id = ?1",nativeQuery = true)
    public void setTag(Integer Id, Tag tag);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET releaseTime = ?2 where id = ?1",nativeQuery = true)
    public void setReleaseTime(Integer Id, Date releaseTime);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET coverPic =  ?2 where id = ?1",nativeQuery = true)
    public void setCoverPic(Integer Id,String coverPic);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET price =  ?2 where id = ?1",nativeQuery = true)
    public void setPrice(Integer Id,double price);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET courseNum =  ?2 where id = ?1",nativeQuery = true)
    public void setCourseNum(Integer Id,int courseNum);

    @Transactional
    @Modifying
    @Query(value = "delete * from course where id = ?1",nativeQuery = true)
    public void deleteCourse(Integer id);

    @Query(value = "select LAST_INSERT_ID()",nativeQuery = true)
    public Integer GetLastInsertId();
}
