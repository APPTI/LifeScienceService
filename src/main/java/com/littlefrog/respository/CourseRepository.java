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

    @Query (value = "SELECT * from course where name like concat('%',?1 ,'%') or name like concat(?1,'%') or name like concat('%',?1) order by releaseTime DESC ",nativeQuery = true)
    public List<Course> findCourseByReleaseTime(String keyword);

    @Query (value = "SELECT * from course where name like concat('%',?1,'%') or name like concat(?1,'%') or name like concat('%',?1) order by popularity desc  ", nativeQuery = true)
    public List<Course> findCourseByPopularity(String keyword);

    @Query (value = "SELECT * from course where name like concat('%',?1,'%') or name like concat(?1 ,'%') or name like concat('%',?1) order by popularity DESC  ",nativeQuery = true)
    public List<Course> findCourseByTagAndPopularity(Tag tag, String keyword);

    @Query (value = "select * from course where name like concat('%',?2,'%') or name like concat(?2,'%') or name like concat('%',?2) and tag=?1 order by releaseTime DESC",nativeQuery = true)
    public List<Course> findCourseByTagAndReleaseTime(Tag tag, String keyword);

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
    @Transactional
    @Modifying
    @Query(value = "delete * from course where id = ?1",nativeQuery = true)
    public void deleteCourse(Integer id);

    @Query(value = "select LAST_INSERT_ID()",nativeQuery = true)
    public Integer GetLastInsertId();
}
