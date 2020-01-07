package com.github.yt.web.controller;

import com.github.yt.web.YtWetDemoApplication;
import com.github.yt.web.common.ControllerTestHandler;
import com.github.yt.web.common.ResultActionsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@ActiveProfiles("packageResponseBodyFalse")
@SpringBootTest(classes = {YtWetDemoApplication.class})
public class PackageResponseBodyFalseTest {

    @Test
    public void classDefaultMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodDefault");
        ResultActionsUtils.notPackaged(resultActions);
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
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodTrue");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classTrueMethodTrue() {

        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodTrue");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classTrueMethodFalse() {

        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodFalse");
        ResultActionsUtils.notPackaged(resultActions);
    }

    @Test
    public void classFalseMethodDefault() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodFalse");
        ResultActionsUtils.notPackaged(resultActions);
    }

    @Test
    public void classFalseMethodTrue() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodTrue");
        ResultActionsUtils.packaged(resultActions);
    }

    @Test
    public void classFalseMethodFalse() {
        ResultActions resultActions = ControllerTestHandler.get("/packageClassDefault/methodFalse");
        ResultActionsUtils.notPackaged(resultActions);
    }

}
