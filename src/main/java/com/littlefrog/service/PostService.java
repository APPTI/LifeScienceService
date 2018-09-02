package com.littlefrog.service;

import com.littlefrog.entity.Post;
import com.littlefrog.respository.PostRepository;
import com.littlefrog.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author DW
 */
@Service("PostService")
public class PostService {
    @Autowired
    private PostRepository postRepository;

    //private Store store = new Store();

    public ArrayList<Post> getAllPost(int lessionID, int index, int offset) {
        return postRepository.findAllPost(lessionID, index, offset);
    }

    public List<Post> getAllReply(Integer lessonId) {
        return postRepository.findAllReply(lessonId);
    }

    public Post addPost(Integer courseID, String content, Integer userID, Integer prePostID) {
        Post post;
        if (prePostID == null) {
            post = new Post(courseID, content, userID);
            try {
                postRepository.save(post);
            } catch (Exception e) {
                return null;
            }
        } else {
            post = new Post(courseID, prePostID, content, userID);
            try {
                postRepository.save(post);
            } catch (Exception e) {
                return null;
            }
        }
        post.setPrePoster(postRepository.findName(userID));
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
        Optional<Post> o = postRepository.findById(postID);
        if (o.isPresent()) {
            Post old = o.get();
            Post post = new Post(old);
            post.setContent(content);
            postRepository.updateContent(postID, content);
            return post;
        }
        return null;
    }

    public boolean removePost(int postID) {
        Optional<Post> p = postRepository.findById(postID);
        if (p.isPresent()) {
            if (p.get().getPreviousPostID() == null) {
                postRepository.deleteReplyPost(postID);
            }
            postRepository.deletePost(postID);
            return true;
        }
        return false;
    }

    public boolean likePost(int postID) {
        Optional<Post> o = postRepository.findById(postID);
        if (o.isPresent()) {
            postRepository.updateLikes(postID);
            Post p = new Post(o.get());
            p.addLike();
            return true;
        }
        return false;
    }

    public Post replyPost(int postID) {
        Optional<Post> o = postRepository.findById(postID);
        if (o.isPresent() && o.get().getPreviousPostID() == null) {
            postRepository.updateReply(postID);
            Post p = new Post(o.get());
            p.addReply();
            return p;
        }
        return null;
    }


    public String getName(Integer userID) {
        return postRepository.findName(userID);
    }
}
