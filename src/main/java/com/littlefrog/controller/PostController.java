package com.littlefrog.controller;

import com.littlefrog.common.Response;
import com.littlefrog.entity.Post;
import com.littlefrog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private Response response;

    @GetMapping("/index")
    public Response index() {
        List<Post> p = postService.getAllPost();
        if (p != null) {
            return genSuccessResult(p);
        } else {
            return genFailResult("没有帖子");
        }
    }

    @PostMapping("/send")
    public Response addPost(@RequestParam Integer lessonId, @RequestParam String content, @RequestParam Integer userID) {
        return genSuccessResult(postService.addPost(lessonId, content, userID));
    }

    @GetMapping("/info")
    public Response indexForID(@RequestParam Integer id) {
        Post post = postService.getPostInfo(id);
        if (post != null) {
            return genSuccessResult(post);
        } else {
            return genFailResult("该帖子不存在");
        }
    }
}
