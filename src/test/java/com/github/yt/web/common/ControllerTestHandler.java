package com.github.yt.web.common;

import com.github.yt.commons.exception.ExceptionUtils;
import com.github.yt.web.result.HttpResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class ControllerTestHandler {
    private static Logger logger = LoggerFactory.getLogger(ControllerTestHandler.class);

    private static ResultActions getResultActions(String url, MultiValueMap<String, String> paramMap, Object code) {
        if (code instanceof Enum) {
            code = HttpResultHandler.getResultConfig().convertErrorCode(ExceptionUtils.getExceptionCode((Enum<?>) code));
        }
        try {
            ResultActions resultActions;
            resultActions = InitApplication.mockMvc.perform(MockMvcRequestBuilders.get(url)
//                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .params(paramMap)
                    .headers(InitApplication.getHttpHeaders()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
            return resultActions;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultActions get(String url, LinkedMultiValueMap<String, String> param, Object code) {
        return getResultActions(url, param, code);
    }

    public static ResultActions get(String url, LinkedMultiValueMap<String, String> param) {
        return getResultActions(url, param, HttpResultHandler.getResultConfig().getDefaultSuccessCode());
    }

    public static ResultActions get(String url) {
        return get(url, new LinkedMultiValueMap<>(), HttpResultHandler.getResultConfig().getDefaultSuccessCode());
    }

}
