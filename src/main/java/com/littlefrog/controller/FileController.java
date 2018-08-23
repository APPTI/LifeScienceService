package com.littlefrog.controller;

import com.littlefrog.common.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

import static com.littlefrog.common.ResultGenerator.genFailResult;
import static com.littlefrog.common.ResultGenerator.genSuccessResult;

@RestController
public class FileController {
    @Value("${web.upload-path}")
    private String rootPath;

    @Value("${appid}")
    private String appid;

    @PostMapping("api/file/uploadfile")
    public Response uploadfile(@RequestHeader String appid,@RequestParam("file") MultipartFile file , @RequestParam String path){
        if(!appid.equals(this.appid)){
            return genFailResult("错误的appid");
        }
        String contentType = file.getContentType();   //图片文件类型
        String fileName = file.getOriginalFilename();  //图片名字

        //文件存放路径
        String filePath = rootPath+path;

        //调用文件处理类FileUtil，处理文件，将文件写入指定位置
        try {
            this.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            return genFailResult(e.getMessage());
        }

        // 返回图片的存放路径
        return genSuccessResult(path);
    }

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{

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
