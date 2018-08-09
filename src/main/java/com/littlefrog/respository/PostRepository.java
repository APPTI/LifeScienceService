package com.littlefrog.respository;

import com.littlefrog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author DW
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Override
    @Query(value = "SELECT * from post", nativeQuery = true)
    List<Post> findAll();

    @Override
    @Query(value = "SELECT * FROM post a where a.postID = ?", nativeQuery = true)
    Optional<Post> findById(Integer id);


    @Modifying
    @Query(value = "UPDATE post SET content =  ?2 where postID = ?1;select * from post where postID = ?1",nativeQuery = true)
    Post updateInfo(Integer postID,String content);

    @Query(value = "select LAST_INSERT_ID();",nativeQuery = true)
     Integer getLastInsertId();

    @Modifying
    @Query(value = "delete * from post where postID = ?1",nativeQuery = true)
    void deleteUser(Integer id);
}
