package com.github.yt.web.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("fileUpload")
public class FileUploadController {

    @PostMapping(value = "uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        System.out.println(new String(file.getBytes()));
    }

}
