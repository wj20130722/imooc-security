package com.imooc.web.controller;

import com.imooc.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjie on 2018/6/28.
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getContentType());
        System.out.println(file.getSize());

        //处理上传文件
        File localFile = new File(System.currentTimeMillis()+".txt");
        file.transferTo(localFile);
        return new FileInfo(localFile.getCanonicalPath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        try(InputStream ins = new FileInputStream(id+".txt");
            OutputStream ops = response.getOutputStream();){
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            IOUtils.copy(ins,ops);
            ops.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
