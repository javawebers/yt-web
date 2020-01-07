package com.github.yt.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Component
public class InitApplication implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private WebApplicationContext webApplicationContext;

    public static MockMvc mockMvc;
    public static HttpHeaders httpHeaders;
    public static MockHttpSession session;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        session = new MockHttpSession();

        if (mockMvc == null) {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }
        if (httpHeaders == null) {
            httpHeaders = new HttpHeaders();
        }
    }

    public static HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

}
