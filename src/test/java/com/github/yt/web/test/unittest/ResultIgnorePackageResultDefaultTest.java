package com.github.yt.web.test.unittest;

import com.github.yt.web.test.YtWebTestApplication;
import com.github.yt.web.result.HttpResultHandler;
import com.github.yt.web.result.SimpleResultConfig;
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
 * 忽略配置类型返回值的包装
 */
@ActiveProfiles("ignorePackageResultType")
@SpringBootTest(classes = { YtWebTestApplication.class})
@AutoConfigureMockMvc
public class ResultIgnorePackageResultDefaultTest extends AbstractTestNGSpringContextTests {
    private SimpleResultConfig resultConfig = new SimpleResultConfig();

    @BeforeMethod
    public void beforeClass() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        // 将静态属性设置为空，避免 ActiveProfiles 不生效
        Field field = HttpResultHandler.class.getDeclaredField("resultConfig");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    public void success() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/resultIgnorePackageResult/success", false);
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$.id",
                Matchers.nullValue()));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$.name",
                Matchers.nullValue()));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void error() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/resultIgnorePackageResult/error");
    }

}
