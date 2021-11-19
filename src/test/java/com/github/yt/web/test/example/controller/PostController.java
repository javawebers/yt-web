package com.github.yt.web.test.example.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    @PostMapping("noParam")
    public void noParam() {

    }

    @PostMapping("param")
    public User param(@RequestBody User user) {
        return user;
    }

    @PostMapping("userList")
    public List<User> userList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        return userList;
    }


    public static class User {
        private String username = "张三";

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

}
