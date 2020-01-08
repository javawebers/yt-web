package com.github.yt.web.example.controller;

import com.github.yt.commons.exception.BaseAccidentException;
import com.github.yt.web.example.entity.WebQueryEntity;
import com.github.yt.web.exception.MyBusinessExceptionEnum;
import com.github.yt.web.query.WebQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
