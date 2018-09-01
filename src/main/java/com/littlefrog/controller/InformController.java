package com.littlefrog.controller;

import com.littlefrog.common.Category;
import com.littlefrog.common.Response;
import com.littlefrog.entity.Inform;
import com.littlefrog.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

/**
 * @author DW
 */
@RequestMapping("api/inform")
@RestController
@CrossOrigin
public class InformController {
    @Autowired
    private InformService informService;
    @Value("${appid}")
    private String appID;

    @GetMapping("/index")
    public Response index(@RequestHeader String appid, @RequestParam Integer userID) {
        if (!appid.equals(appID)) {
            return genFailResult("错误的appid");
        }
        ArrayList<Inform> arrayList = informService.getAllInform(userID);
        if (arrayList != null && arrayList.size() != 0) {
            return genSuccessResult(arrayList);
        } else {
            return genFailResult("没有通知");
        }
    }

    @PostMapping("/delete")
    public Response delete(@RequestHeader String appid, @RequestParam Integer informID) {
        if (!appid.equals(appID)) {
            return genFailResult("错误的appid");
        }
        if (informService.deleteInform(informID)) {
            return genSuccessResult();
        } else {
            return genFailResult("原通知不存在");
        }
    }

    /**
     * @param userID 为-1则通知所有人
     */
    @PostMapping("/newInform")
    public Response creat(@RequestHeader String appid, @RequestParam Integer userID, @RequestParam String content, @RequestParam String category, @RequestParam(required = false) Integer returnID) {
        if (!appid.equals(appID)) {
            return genFailResult("错误的appid");
        }
        Category c;
        try {
            c = Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            return genFailResult("枚举参数无效");
        }
        if (informService.addInform(userID, content, c, returnID)) {
            return genSuccessResult();
        } else {
            return genFailResult("信息添加失败");
        }
    }
}
