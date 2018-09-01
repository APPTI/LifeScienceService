package com.littlefrog.respository;

import com.littlefrog.entity.Course;
import com.littlefrog.entity.Lesson;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.littlefrog.common.Tag;
import com.littlefrog.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson,Integer>{

    @Query (value = "SELECT * from lesson u where u.course_id = ?1 order by u.order_num",nativeQuery = true)
    public List<Lesson> findAll(int course_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE lesson SET title = ?2 where id = ?1",nativeQuery = true)
    public void setTitle(Integer Id, String title);

    @Transactional
    @Modifying
    @Query(value = "UPDATE lesson SET video_url = ?2 where id = ?1",nativeQuery = true)
    public void setVedioUrl(Integer Id, String Url);

    @Transactional
    @Modifying
    @Query(value = "UPDATE lesson SET description = ?2 where id = ?1",nativeQuery = true)
    public void setDescription(Integer Id, String description);

    @Transactional
    @Modifying
    @Query(value = "UPDATE lesson SET cover_pic = ?2 where id = ?1",nativeQuery = true)
    public void setCover_url(Integer Id, String cover_url);

    @Transactional
    @Modifying
    @Query(value = "UPDATE lesson SET title =  ?2, video_url=?3, description=?4, cover_pic=?5 where id = ?1",nativeQuery = true)
    public void setLessonInfo(Integer id,String title, String video_url, String description, String cover_url);

    @Transactional
    @Modifying
    @Query(value = "delete * from lesson where course_id = ?1",nativeQuery = true)
    public void deleteLessonsByCourse_id(Integer courseId);

    @Transactional
    @Modifying
    @Query(value = "delete * from lesson where id = ?1",nativeQuery = true)
    public void deleteLesson(Integer id);

    @Query(value = "select LAST_INSERT_ID()",nativeQuery = true)
    public Integer GetLastInsertId();


}
