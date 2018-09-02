package com.littlefrog.respository;

import com.littlefrog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author DW
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    /**
     * @return 所有根话题
     */
    @Query(value = "SELECT * from post where previous_post_id IS NULL and course_id=? order by post_id desc ", nativeQuery = true)
    ArrayList<Post> findAllPost(Integer lessonId);

    /**
     * @param postID 一个根话题
     */
    @Query(value = "SELECT * from post  where previous_post_id =? ", nativeQuery = true)
    ArrayList<Post> findAllReply(Integer postID);

    /**
     * @return 指定id的post
     */
    @Override
    @Query(value = "SELECT * FROM post  where post_id = ?", nativeQuery = true)
    Optional<Post> findById(Integer postID);

    @Query(value = "SELECT name FROM user  where user_id = ?", nativeQuery = true)
    String findName(Integer id);


    @Transactional
    @Modifying
    @Query(value = "UPDATE post SET content =  ?2 where post_id = ?1 ", nativeQuery = true)
    void updateContent(Integer postID, String content);

    @Transactional
    @Modifying
    @Query(value = "UPDATE post SET likes =likes + 1  where post_id = ?", nativeQuery = true)
    void updateLikes(Integer postID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE post SET reply =reply + 1  where post_id = ?", nativeQuery = true)
    void updateReply(Integer postID);

    @Transactional
    @Modifying
    @Query(value = "delete from post where post_id = ?", nativeQuery = true)
    void deletePost(Integer postID);

    @Transactional
    @Modifying
    @Query(value = "delete from post where previous_post_id = ?", nativeQuery = true)
    void deleteReplyPost(Integer prePostID);
}
