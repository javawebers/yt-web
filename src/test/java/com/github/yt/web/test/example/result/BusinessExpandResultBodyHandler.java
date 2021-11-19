package com.github.yt.web.test.example.result;

import com.github.yt.web.result.BaseExpandResultBodyHandler;
import com.github.yt.web.result.HttpResultEntity;
import org.springframework.stereotype.Component;

@Component
public class BusinessExpandResultBodyHandler implements BaseExpandResultBodyHandler {
    @Override
    public void expandResultBody(HttpResultEntity resultBody) {
        resultBody.put("expandResultBody", System.currentTimeMillis());
    }
}
