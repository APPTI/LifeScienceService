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
    @Value("${appID}")
    private String appID;

    @GetMapping("/index")
    public Response index(@RequestHeader String appID, @RequestParam Integer userID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        ArrayList<Inform> arrayList = informService.getAllInform(userID);
        if (arrayList != null && arrayList.size() != 0) {
            return genSuccessResult(arrayList);
        } else {
            return genFailResult("没有通知");
        }
    }

    @PostMapping("/delete")
    public Response delete(@RequestHeader String appID, @RequestParam Integer informID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        if (informService.deleteInform(informID)) {
            return genSuccessResult("删除");
        } else {
            return genFailResult("原通知不存在或者通知全体用户");
        }
    }

    @PostMapping("/deleteAllInfo")
    public Response deleteAll(@RequestHeader String appID, @RequestParam Integer userID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        if (userID == -1) {
            return genFailResult("用户id不可为全体");
        }
        informService.deleteAllInform(userID);
        return genSuccessResult("SUCCESS");
    }

    /**
     * @param userID 为-1则通知所有人
     */
    @PostMapping("/newInform")
    public Response create(@RequestHeader String appID, @RequestParam Integer userID, @RequestParam String content, @RequestParam String category, @RequestParam(required = false) Integer returnID) {
        if (!appID.equals(this.appID)) {
            return genFailResult("错误的appID");
        }
        Category c;
        try {
            c = Category.valueOf(category.toUpperCase());
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
