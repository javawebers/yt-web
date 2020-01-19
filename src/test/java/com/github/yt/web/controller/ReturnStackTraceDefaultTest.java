package com.github.yt.web.controller;

import com.github.yt.web.YtWetDemoApplication;
import com.github.yt.web.common.ControllerTestHandler;
import com.github.yt.web.result.HttpResultHandler;
import org.hamcrest.Matchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.Test;

/**
 * 和 ResultClassBusinessTest 互斥，不能同时执行
 */
@ActiveProfiles("default")
@SpringBootTest(classes = {YtWetDemoApplication.class})
public class ReturnStackTraceDefaultTest extends AbstractTestNGSpringContextTests {

    @Test
    public void success() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/returnStackTrace/success");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$",
                Matchers.not(Matchers.hasKey(HttpResultHandler.getResultConfig().getStackTraceField()))));
    }

    @Test
    public void error() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/returnStackTrace/error");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$",
                Matchers.not(Matchers.hasKey(HttpResultHandler.getResultConfig().getStackTraceField()))));
    }

    @Test
    public void knowException() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/returnStackTrace/knowException");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$",
                Matchers.not(Matchers.hasKey(HttpResultHandler.getResultConfig().getStackTraceField()))));
    }
}
