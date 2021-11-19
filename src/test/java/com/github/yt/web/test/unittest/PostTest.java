package com.github.yt.web.test.unittest;

import com.github.yt.web.test.YtWebTestApplication;
import com.github.yt.web.test.example.controller.PostController;
import com.github.yt.web.unittest.ControllerTestHandler;
import com.github.yt.web.unittest.HttpRestHandler;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.ResultActions;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ActiveProfiles("default")
@SpringBootTest(classes = { YtWebTestApplication.class})
@AutoConfigureMockMvc
public class PostTest extends AbstractTestNGSpringContextTests {

    @Test
    public void noParam() {
        ResultActions resultActions = ControllerTestHandler.post("/post/noParam");
    }

    @Test
    public void param() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", "张三");
        user.put("password", "123456");
        ResultActions resultActions = ControllerTestHandler.post("/post/param", user);
    }

    @Test
    public void param2() {
        ResultActions resultActions = ControllerTestHandler.post("/post/param", "{}");
        PostController.User user = HttpRestHandler.getResult(resultActions, PostController.User.class);
    }

    @Test
    public void userList() {
        ResultActions resultActions = ControllerTestHandler.post("/post/userList");
        List<PostController.User> userList = HttpRestHandler.getListResult(resultActions, PostController.User.class);
        userList.forEach(user -> System.out.println(user.getUsername()));
    }

}
