package com.littlefrog.service;

import com.littlefrog.entity.Post;
import com.littlefrog.respository.PostRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DW
 */
@Service("PostService")
public class PostService {
    @Autowired
    private PostRepository postRepository;
    private Store store;

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public Post addPost(Integer lessonId, String content, Integer userID){
        Post post = new Post( lessonId,  content,  userID);
        //可能要缓存在这里
        postRepository.save(post);
        return post;
    }

    public Post getPostInfo(int postID){
        if (postRepository.findById(postID).isPresent() ){
            return postRepository.findById(postID).get();
        }
        return null;
    }

    /**
     * 修改帖子内容
     * @return 修改后的帖子
     */
    public Post setUserInfo(Integer postID, String content){
        Post post = postRepository.updateInfo(postID,content);
        //可能要缓存在这里
        return post;
    }
}
