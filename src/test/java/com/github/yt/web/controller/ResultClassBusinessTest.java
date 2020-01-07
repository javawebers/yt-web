package com.github.yt.web.controller;


import com.github.yt.web.YtWetDemoApplication;
import com.github.yt.web.common.ControllerTestHandler;
import com.github.yt.web.example.result.BusinessResultConfig;
import com.github.yt.web.exception.MyBusinessExceptionEnum;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * 和 ResultClassDefaultTest 互斥，不能同时执行
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("resultClass")
@SpringBootTest(classes = {YtWetDemoApplication.class})
public class ResultClassBusinessTest {

    private BusinessResultConfig resultConfig = new BusinessResultConfig();

    @Test
    public void success() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/resultClass/success");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + resultConfig.getErrorCodeField(),
                Matchers.equalTo(resultConfig.getDefaultSuccessCode())));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + resultConfig.getMessageField(),
                Matchers.equalTo(resultConfig.getDefaultSuccessMessage())));
    }

    @Test
    public void error() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/resultClass/error");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + resultConfig.getErrorCodeField(),
                Matchers.equalTo(resultConfig.getDefaultErrorCode())));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + resultConfig.getMessageField(),
                Matchers.equalTo(resultConfig.getDefaultErrorMessage())));
    }

    @Test
    public void knowException() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/resultClass/knowException");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + resultConfig.getErrorCodeField(),
                Matchers.equalTo(resultConfig.convertErrorCode(MyBusinessExceptionEnum.CODE_1003.name()))));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + resultConfig.getMessageField(),
                Matchers.equalTo(MyBusinessExceptionEnum.CODE_1003.message)));
    }

}
