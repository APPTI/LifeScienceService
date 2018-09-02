package com.littlefrog.controller;

import com.littlefrog.common.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
@CrossOrigin
public class FileController {
    @Value("${web.upload-path}")
    private String rootPath;

    @Value("${appID}")
    private String appID;

    @PostMapping("api/file/uploadFile")
    public Response uploadFile(@RequestHeader String appID,@RequestParam("file") MultipartFile file , @RequestParam String path){
//        if(!appID.equals(this.appID)){
//            return genFailResult("错误的appID");
//        }
        String contentType = file.getContentType();   //图片文件类型
        String fileName = file.getOriginalFilename();  //图片名字

        //文件存放路径
        String filePath = rootPath+path;

        //调用文件处理类FileUtil，处理文件，将文件写入指定位置
        try {
            this.saveFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            return genFailResult(e.getMessage());
        }

        // 返回图片的存放路径
        return genSuccessResult((path+fileName));
    }

    public static void saveFile(byte[] file, String filePath, String fileName) throws Exception{

        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
