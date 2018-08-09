package com.littlefrog.service;

import com.littlefrog.entity.Post;
import com.littlefrog.respository.PostRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Post addPost(Integer lessonId, String content, Integer userID) {
        Post post = new Post(lessonId, content, userID);
        //可能要缓存在这里
        postRepository.save(post);
        return post;
    }

    public Post getPostInfo(int postID) {
        Optional<Post> o = postRepository.findById(postID);
        return o.orElse(null);
    }

    /**
     * 修改帖子内容
     *
     * @return 修改后的帖子
     */
    public Post setUserInfo(Integer postID, String content) {
        postRepository.updateInfo(postID, content);
        Optional<Post> o = postRepository.findById(postID);
        //可能要缓存在这里
        return o.orElse(null);
    }

    public void removePost(int postID){
        postRepository.deletePost(postID);
    }
}
