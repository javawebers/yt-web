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
public class WebPageQueryDefaultTest extends AbstractTestNGSpringContextTests {

    @Test
    public void test1() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/webPageQuery/success?pageNo=2&pageSize=3");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getResultField() + ".pageNo",
                Matchers.equalTo(2)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getResultField() + ".pageSize",
                Matchers.equalTo(3)));
    }

    @Test
    public void test2() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/webPageQuery/success?pageSize=3");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getResultField() + ".pageNo",
                Matchers.equalTo(1)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getResultField() + ".pageSize",
                Matchers.equalTo(3)));
    }

    @Test
    public void test3() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/webPageQuery/success");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getResultField() + ".pageNo",
                Matchers.equalTo(1)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getResultField() + ".pageSize",
                Matchers.equalTo(10)));
    }

    @Test
    public void test4() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/webPageQuery/success?myPageNo=2&myPageSize=3");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getResultField() + ".pageNo",
                Matchers.equalTo(1)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getResultField() + ".pageSize",
                Matchers.equalTo(10)));
    }


}
