package com.github.yt.web.test.common;

import com.github.yt.web.result.HttpResultHandler;
import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ResultActionsUtils {

    public static void packaged(ResultActions resultActions) {
        try {
            resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                    "$." + HttpResultHandler.getResultConfig().getErrorCodeField(),
                    Matchers.equalTo(HttpResultHandler.getResultConfig().getDefaultSuccessCode())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void notPackaged(ResultActions resultActions) {
        try {
            resultActions.andExpect(MockMvcResultMatchers.content().string(""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void defaultErrorPackaged(ResultActions resultActions) {
        try {
            resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                    "$." + HttpResultHandler.getResultConfig().getErrorCodeField(),
                    Matchers.equalTo(HttpResultHandler.getResultConfig().getDefaultErrorCode())));
            resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                    "$." + HttpResultHandler.getResultConfig().getMessageField(),
                    Matchers.equalTo(HttpResultHandler.getResultConfig().getDefaultErrorMessage())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void resultSinglePackage(ResultActions resultActions) {
        try {
            resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                    "$." + HttpResultHandler.getResultConfig().getErrorCodeField(),
                    Matchers.equalTo(HttpResultHandler.getResultConfig().getDefaultSuccessCode())));
            resultActions.andExpect(MockMvcResultMatchers.jsonPath(
                    "$." + HttpResultHandler.getResultConfig().getResultField(),
                    Matchers.equalTo(1)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
