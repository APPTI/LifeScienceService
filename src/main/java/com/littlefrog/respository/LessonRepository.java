package com.littlefrog.respository;

import com.littlefrog.entity.Course;
import com.littlefrog.entity.Lesson;
import org.springframework.data.jpa.repository.Query;
import com.littlefrog.common.Tag;
import com.littlefrog.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson,Integer>{

    @Query (value = "SELECT * from lesson u where u.course_id = ?1 order by u.order",nativeQuery = true)
    public List<Lesson> findAll(int course_id);


}
