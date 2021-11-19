package com.github.yt.web.unittest;

import com.github.yt.web.util.SpringContextUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 单元测试初始化 mock 对象
 * @author sheng
 */
@Component("ytWebTestInitApplication")
public class InitApplication {
    public final MockMvc mockMvc;
    public final HttpHeaders httpHeaders;
    public final MockHttpSession session;

    public InitApplication(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.httpHeaders = new HttpHeaders();
        this.session = new MockHttpSession();
    }

    public static HttpHeaders getHttpHeaders() {
        return SpringContextUtils.getBean(InitApplication.class).httpHeaders;
    }

    public static MockMvc getMockMvc() {
        return SpringContextUtils.getBean(InitApplication.class).mockMvc;
    }

    public static MockHttpSession getSession() {
        return SpringContextUtils.getBean(InitApplication.class).session;
    }
}
