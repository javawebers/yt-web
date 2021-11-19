package com.github.yt.web.test.example.controller;

import com.github.yt.web.test.example.entity.WebQueryEntity;
import com.github.yt.web.test.query.WebQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("webPageQuery")
public class WebPageQueryController {

    @GetMapping("success")
    public WebQueryEntity success(WebQuery webQuery) {
        return new WebQueryEntity()
                .setPageNo(webQuery.takePageNo())
                .setPageSize(webQuery.takePageSize());
    }

}
