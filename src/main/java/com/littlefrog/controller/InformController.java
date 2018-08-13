package com.littlefrog.controller;

import com.littlefrog.common.Page;
import com.littlefrog.common.Response;
import com.littlefrog.entity.Inform;
import com.littlefrog.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

/**
 * @author DW
 */
@RequestMapping("/inform")
@RestController
public class InformController {
    @Autowired
    private InformService informService;

    @GetMapping("/index")
    public Response index(@RequestParam Integer userID) {
        ArrayList<Inform> arrayList = informService.getAllInform(userID);
        if (arrayList != null && arrayList.size() != 0) {
            return genSuccessResult(arrayList);
        } else {
            return genFailResult("没有通知");
        }
    }

    @PostMapping("/delete")
    public Response delete(@RequestParam Integer informID) {
        if (informService.deleteInform(informID)) {
            return genSuccessResult();
        } else {
            return genFailResult("原通知不存在");
        }
    }

    @PostMapping("/newInform")
    public Response creat(@RequestParam Integer userID, @RequestParam String content, Page page, int returnID) {
        informService.addInform(userID, content, page, returnID);
        return genSuccessResult();
    }
}
