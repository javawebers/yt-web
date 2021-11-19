package com.github.yt.web.test.unittest;

import com.github.yt.web.test.YtWebTestApplication;
import com.github.yt.web.test.common.ResultActionsUtils;
import com.github.yt.web.result.HttpResultHandler;
import com.github.yt.web.unittest.ControllerTestHandler;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.ResultActions;
import org.testng.annotations.Test;

@ActiveProfiles("packageResponseBodyFalse")
@SpringBootTest(classes = { YtWebTestApplication.class})
@Test(priority = 2)
@AutoConfigureMockMvc
public class PackageResponseBodyFalseTest extends AbstractTestNGSpringContextTests {

    @Test
    public void classDefaultMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodDefault", false);
        ResultActionsUtils.notPackaged(resultActions);
    }

    @Test
    public void classDefaultMethodTrue() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodTrue");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classDefaultMethodFalse() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodFalse", false);
        ResultActionsUtils.notPackaged(resultActions);
    }


    @Test
    public void classTrueMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassTrue/methodDefault");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classTrueMethodTrue() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassTrue/methodTrue");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classTrueMethodFalse() {

        ResultActions resultActions = ControllerTestHandler.get("/packageClassTrue/methodFalse", false);
        ResultActionsUtils.notPackaged(resultActions);
    }


    @Test
    public void classFalseMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassFalse/methodDefault", false);
        ResultActionsUtils.notPackaged(resultActions);
    }

    @Test
    public void classFalseMethodTrue() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassFalse/methodTrue");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classFalseMethodFalse() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassFalse/methodFalse", false);
        ResultActionsUtils.notPackaged(resultActions);
    }

    // HttpResultEntity
    @Test
    public void classDefaultEntityMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/entityMethodDefault");
        ResultActionsUtils.resultSinglePackage(resultActions);
    }

    @Test
    public void classDefaultEntityMethodTrue() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/entityMethodTrue");
        ResultActionsUtils.resultSinglePackage(resultActions);
    }
    @Test
    public void classDefaultEntityMethodFalse() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/entityMethodFalse");
        ResultActionsUtils.resultSinglePackage(resultActions);
    }

    @Test
    public void classTrueEntityMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassTrue/entityMethodDefault");
        ResultActionsUtils.resultSinglePackage(resultActions);
    }

    @Test
    public void classTrueEntityMethodTrue() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassTrue/entityMethodTrue");
        ResultActionsUtils.resultSinglePackage(resultActions);
    }

    @Test
    public void classTrueEntityMethodFalse() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassTrue/entityMethodFalse");
        ResultActionsUtils.resultSinglePackage(resultActions);
    }


    @Test
    public void classFalseEntityMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassFalse/entityMethodDefault");
        ResultActionsUtils.resultSinglePackage(resultActions);
    }

    @Test
    public void classFalseEntityMethodTrue() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassFalse/entityMethodTrue");
        ResultActionsUtils.resultSinglePackage(resultActions);
    }

    @Test
    public void classFalseEntityMethodFalse() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassFalse/entityMethodFalse");
        ResultActionsUtils.resultSinglePackage(resultActions);
    }

    @Test
    public void entityThrowException() throws Exception {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/entityThrowException", HttpResultHandler.getResultConfig().getDefaultErrorCode());
        ResultActionsUtils.defaultErrorPackaged(resultActions);

    }

    @Test(expectedExceptions = RuntimeException.class)
    public void responseEntityThrowException() {
        ControllerTestHandler.get("/packageClassDefault/responseEntityThrowException");
    }

}
