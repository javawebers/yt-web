package com.github.yt.web.controller;

import com.github.yt.web.YtWetDemoApplication;
import com.github.yt.web.common.ControllerTestHandler;
import com.github.yt.web.result.HttpResultHandler;
import com.github.yt.web.result.SimpleResultConfig;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 和 ResultClassBusinessTest 互斥，不能同时执行
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("returnStackTrace")
@SpringBootTest(classes = {YtWetDemoApplication.class})
public class ReturnStackTraceTrueTest {


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
                Matchers.hasKey(HttpResultHandler.getResultConfig().getStackTraceField())));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getStackTraceField(),
                Matchers.notNullValue()));
    }

    @Test
    public void knowException() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/returnStackTrace/knowException");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getStackTraceField(),
                Matchers.notNullValue()));
    }
}
