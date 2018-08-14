package com.littlefrog.controller;

import com.littlefrog.common.Category;
import com.littlefrog.common.Response;
import com.littlefrog.entity.Post;
import com.littlefrog.service.InformService;
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
    @Autowired
    private InformService informService;

    /**
     * @return 课程下所有帖子
     */
    @GetMapping("/index")
    public Response index(@RequestParam Integer courseID, @RequestParam Integer index, @RequestParam Integer offset) {
        ArrayList<Post> p = postService.getAllPost(courseID);
        if (p != null && p.size() >= index) {
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
        if (postService.addPost(courseID, content, userID, null) != null) {
            return genSuccessResult();
        } else {
            return genFailResult("发送失败");
        }
    }

    /**
     * reply后应该重新调用 getReply刷新页面
     */
    @PostMapping("/reply")
    public Response reply(@RequestParam Integer courseID, @RequestParam String content, @RequestParam Integer userID, @RequestParam Integer prePostID) {
        if (postService.getName(userID) != null) {
            Post post;
            if (postService.replyPost(prePostID) != null) {
                if ((post = postService.addPost(courseID, content, userID, prePostID)) != null) {
                    if (informService.addInform(userID, "用户  " + post.getPrePoster() + "  回复了你。", Category.POST, prePostID)) {
                        return genSuccessResult();
                    } else {
                        return genFailResult("回复成功但是添加通知失败");
                    }
                } else {
                    return genFailResult("帖子添加失败");
                }
            }
            return genFailResult("没有帖子可回复(被回复的帖子被删除等） 或者 被回复的帖子为子贴");
        }
        return genFailResult("回帖用户不存在");

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
            return genSuccessResult(p);
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
    public Response like(@RequestParam Integer postID, @RequestParam Integer userID) {
        if (postService.getName(userID) != null) {
            if (postService.likePost(postID)) {
                if (informService.addInform(postService.getPostInfo(postID).getUserID(), "用户  " + postService.getName(userID) + "  喜欢了你发布的评论。", Category.POST, postID)) {
                    return genSuccessResult();
                } else {
                    return genFailResult("喜欢但是添加通知失败");
                }
            } else {
                return genFailResult("帖子不存在");
            }
        }else {
            return genFailResult("点赞用户不存在");
        }
    }
}
