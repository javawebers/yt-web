package com.github.yt.web.controller;

import com.github.yt.web.YtWetDemoApplication;
import com.github.yt.web.common.ControllerTestHandler;
import com.github.yt.web.common.ResultActionsUtils;
import com.github.yt.web.result.HttpResultHandler;
import com.github.yt.web.result.SimpleResultConfig;
import org.hamcrest.Matchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.Test;

@ActiveProfiles("packageResponseBodyTrue")
@SpringBootTest(classes = {YtWetDemoApplication.class})
public class PackageResponseBodyTrueTest extends AbstractTestNGSpringContextTests {

    @Test
    public void classDefaultMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodDefault");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classDefaultMethodTrue() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodTrue");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classDefaultMethodFalse() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodFalse");
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

        ResultActions resultActions = ControllerTestHandler.get("/packageClassTrue/methodFalse");
        ResultActionsUtils.notPackaged(resultActions);
    }


    @Test
    public void classFalseMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassFalse/methodDefault");
        ResultActionsUtils.notPackaged(resultActions);
    }

    @Test
    public void classFalseMethodTrue() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassFalse/methodTrue");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classFalseMethodFalse() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassFalse/methodFalse");
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
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/entityThrowException");
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getErrorCodeField(),
                Matchers.equalTo(HttpResultHandler.getResultConfig().getDefaultErrorCode())));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                "$." + HttpResultHandler.getResultConfig().getMessageField(),
                Matchers.equalTo(HttpResultHandler.getResultConfig().getDefaultErrorMessage())));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void responseEntityThrowException() {
        ControllerTestHandler.get("/packageClassDefault/responseEntityThrowException");
    }

}
