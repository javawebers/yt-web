package com.github.yt.web.test.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("fileUpload")
@Slf4j
public class FileUploadController {


    @PostMapping(value = "uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        log.warn(new String(file.getBytes()));
    }

}
