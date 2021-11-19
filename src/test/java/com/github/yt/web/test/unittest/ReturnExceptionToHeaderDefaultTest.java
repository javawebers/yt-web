package com.github.yt.web.test.unittest;

import com.github.yt.web.result.PackageResponseBodyAdvice;
import com.github.yt.web.test.YtWebTestApplication;
import com.github.yt.web.test.exception.MyBusinessExceptionEnum;
import com.github.yt.web.unittest.ControllerTestHandler;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.Test;

@ActiveProfiles("default")
@SpringBootTest(classes = { YtWebTestApplication.class})
@AutoConfigureMockMvc
public class ReturnExceptionToHeaderDefaultTest extends AbstractTestNGSpringContextTests {


    @Test
    public void success() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/returnStackTrace/success");
        resultActions.andExpect(MockMvcResultMatchers.header().exists(PackageResponseBodyAdvice.HEADER_YT_WEB_EXCEPTION));
    }

    @Test
    public void knowException() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/returnStackTrace/knowException", MyBusinessExceptionEnum.CODE_1003);
        resultActions.andExpect(MockMvcResultMatchers.header().exists(PackageResponseBodyAdvice.HEADER_YT_WEB_EXCEPTION));
    }
}
