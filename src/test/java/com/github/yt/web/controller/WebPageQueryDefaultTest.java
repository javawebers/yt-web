package com.github.yt.web.controller;

import com.github.yt.web.YtWetDemoApplication;
import com.github.yt.web.common.ControllerTestHandler;
import com.github.yt.web.query.WebQuery;
import com.github.yt.web.result.HttpResultHandler;
import com.sun.webkit.WebPage;
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
@ActiveProfiles("default")
@SpringBootTest(classes = {YtWetDemoApplication.class})
public class WebPageQueryDefaultTest {

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
