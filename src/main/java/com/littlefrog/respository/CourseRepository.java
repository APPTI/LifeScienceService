package com.littlefrog.respository;


import com.littlefrog.common.Tag;
import com.littlefrog.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface CourseRepository extends JpaRepository<Course,Integer>{
    //查
    @Query (value = "SELECT * from course",nativeQuery = true)
    public List<Course> findAll();

    @Query (value = "SELECT * from course where name like concat('%',?1 ,'%') or name like concat(?1,'%') or name like concat('%',?1) order by release_time DESC ",nativeQuery = true)
    public List<Course> findCourseByReleaseTime(String keyword);

    @Query (value = "SELECT * from course where name like concat('%',?1,'%') or name like concat(?1,'%') or name like concat('%',?1) order by popularity desc ", nativeQuery = true)
    public List<Course> findCourseByPopularity(String keyword);

    @Query (value = "SELECT * from course t where t.tag = ?1 AND (t.name like concat('%',?2,'%') or t.name like concat(?2,'%') or t.name like concat('%',?2)) order by popularity DESC",nativeQuery = true)
    public List<Course> findCourseByTagAndPopularity(int tag, String keyword);

    @Query (value = "select * from course t where t.tag = ?1 AND (t.name like concat('%',?2,'%') or t.name like concat(?2,'%') or t.name like concat('%',?2)) order by release_time DESC",nativeQuery = true)
    public List<Course> findCourseByTagAndReleaseTime(int tag, String keyword);

    @Query(value = "select * from course where name like concat('%',?1,'%') or name like concat(?1 ,'%') or name like concat('%',?1) order by tag  ",nativeQuery = true)
    public List<Course> findCourseByTag(String keyword);

    @Query (value = "SELECT * FROM course a  where a.id = ?1",nativeQuery = true)
    public Course findByCourseId(Integer id);

    //add
    @Transactional
    @Modifying
    @Query(value = "UPDATE course  c " +
            "SET c.location =  ?2 ,c.name = ?3 , c.teacher = ?4 ,c.introduction = ?5 ,c.popularity=?6 ,c.tag=?7 , c.cover_pic = ?8 ,c.release_time = ?9, c.price = ?10, c.course_num = ?11 " +
            "where id = ?1",nativeQuery = true)
    public void setCourse(Integer Id,String location,String name, String teacher,String introduction, int popularity, Tag tag, String coverPic, Date releaseTime, int price, int courseNum);

    //delete
    @Query(value = "UPDATE course SET name = ?2 where id = ?1",nativeQuery = true)
    public void setName(Integer Id, String newName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET teacher = ?2 where id = ?1",nativeQuery = true)
    public void setTeacherName(Integer Id, String teacherName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET popularity = ?2 where id = ?1",nativeQuery = true)
    public void setPopularity(Integer Id, int popularity);

    @Transactional
    @Modifying
    @Query(value = "UPDATE course SET introduction = ?2 where id = ?1",nativeQuery = true)
    public void setIntroduction(Integer Id, String introduction);

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
    @Query(value = "UPDATE course SET cover_pic =  ?2 where id = ?1",nativeQuery = true)
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
    @Query(value = "UPDATE course SET location =  ?2, name=?3, teacher=?4, introduction=?5, popularity=?6, tag=?7, cover_pic=?8, price=?9, course_num=?10 where id = ?1",nativeQuery = true)
    public void setCourseInfo(Integer id,String location, String name, String teacher, String introduction, int popularity, int tag, String cover_pic,double price, int course_num);

    @Transactional
    @Modifying
    @Query(value = "delete * from course where id = ?1",nativeQuery = true)
    public void deleteCourse(Integer id);

    @Query(value = "select LAST_INSERT_ID()",nativeQuery = true)
    public Integer GetLastInsertId();
}
