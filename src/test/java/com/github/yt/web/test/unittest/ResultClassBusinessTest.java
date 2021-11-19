package com.github.yt.web.test.unittest;


import com.github.yt.web.test.YtWebTestApplication;
import com.github.yt.web.test.example.result.BusinessResultConfig;
import com.github.yt.web.test.exception.MyBusinessExceptionEnum;
import com.github.yt.web.result.HttpResultHandler;
import com.github.yt.web.unittest.ControllerTestHandler;
import org.hamcrest.Matchers;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

/**
 * 和 ResultClassDefaultTest 互斥，不能同时执行
 */
@ActiveProfiles("resultClass")
@SpringBootTest(classes = { YtWebTestApplication.class})
@AutoConfigureMockMvc
public class ResultClassBusinessTest extends AbstractTestNGSpringContextTests {

    private BusinessResultConfig resultConfig = new BusinessResultConfig();

    @BeforeMethod
    public void beforeClass() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        // 将静态属性设置为空，避免 ActiveProfiles 不生效
        Field field = HttpResultHandler.class.getDeclaredField("resultConfig");
        field.setAccessible(true);
        field.set(null, null);
    }

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
        ResultActions resultActions = ControllerTestHandler.get("/resultClass/error", resultConfig.getDefaultErrorCode());
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + resultConfig.getMessageField(),
                Matchers.equalTo(resultConfig.getDefaultErrorMessage())));
    }

    @Test
    public void knowException() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/resultClass/knowException", MyBusinessExceptionEnum.CODE_1003);
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + resultConfig.getMessageField(),
                Matchers.equalTo(MyBusinessExceptionEnum.CODE_1003.getMessage())));
    }

}
