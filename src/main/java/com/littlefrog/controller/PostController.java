package com.littlefrog.controller;

import com.littlefrog.common.Response;
import com.littlefrog.entity.Post;
import com.littlefrog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

/**
 * @author DW
 */
@RequestMapping("/post")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * @return 课程下所有帖子
     */
    @GetMapping("/index")
    public Response index(@RequestParam Integer courseID, @RequestParam Integer index, @RequestParam Integer offset) {
        ArrayList<Post> p = postService.getAllPost(courseID);
        if (p != null && p.size() != 0) {
            Post[] posts = new Post[p.size()];
            p.toArray(posts);
            Post[] give = new Post[offset];
            if (index - 1 + offset >= posts.length) {
                System.arraycopy(posts, index - 1, give, 0, posts.length - index + 1);
            } else {
                System.arraycopy(posts, index - 1, give, 0, offset);
            }
            return genSuccessResult(give);
        } else {
            return genFailResult("没有帖子");
        }
    }

    @PostMapping("/send")
    public Response send(@RequestParam Integer courseID, @RequestParam String content, @RequestParam Integer userID) {
        postService.addPost(courseID, content, userID, null);
        return genSuccessResult();
    }

    /**
     * reply后应该重新调用 getReply刷新页面
     */
    @PostMapping("/reply")
    public Response reply(@RequestParam Integer courseID, @RequestParam String content, @RequestParam Integer userID, @RequestParam Integer prePostID) {
        //此处应该有通知
        if (postService.replyPost(prePostID)) {
            postService.addPost(courseID, content, userID, prePostID);
            return genSuccessResult();
        }
        return genFailResult("没有帖子可回复(被回复的帖子被删除等） 或者 被回复的帖子为子贴");
    }

    @GetMapping("/getReply")
    public Response getReply(@RequestParam Integer postID) {
        List<Post> list = postService.getAllReply(postID);
        if (list != null && list.size() != 0) {
            return genSuccessResult(list);
        } else {
            return genFailResult("没有回复帖子");
        }
    }

    @GetMapping("/info")
    public Response indexForID(@RequestParam Integer postID) {
        Post post = postService.getPostInfo(postID);
        if (post != null) {
            return genSuccessResult(post);
        } else {
            return genFailResult("该帖子不存在");
        }
    }

    /**
     * 修改评论(可调用返回值直接刷新原post)
     */
    @PostMapping("/edit")
    public Response modify(@RequestParam Integer postID, @RequestParam String newContent) {
        Post p = postService.setUserInfo(postID, newContent);
        if (p != null) {
            return genSuccessResult();
        } else {
            return genFailResult("原贴不存在");
        }
    }

    @PostMapping("/delete")
    public Response delete(@RequestParam Integer postID) {
        if (postService.removePost(postID)) {
            return genSuccessResult();
        } else {
            return genFailResult("帖子不存在");
        }
    }

    @PostMapping("/like")
    public Response like(@RequestParam Integer postID) {
        if (postService.likePost(postID)) {
            return genSuccessResult();
        } else {
            return genFailResult("帖子不存在");
        }
    }
}
