package com.github.yt.web.test.example;

import com.github.yt.web.unittest.ControllerTestHandler;

import java.util.List;

public class TestControllerTestHandler {
    public static void main(String[] args) {
        Object o = new TestControllerTestHandler.User().setUsername("32咋会给你三");
        System.out.println(ControllerTestHandler.parseToUrlPair(o));
        System.out.println(ControllerTestHandler.parseToUrlPair("localhost", o));
        System.out.println(ControllerTestHandler.parseToUrlPair("localhost?", o));
        System.out.println(ControllerTestHandler.parseToUrlPair("localhost?p=3", o));



    }


    static class User {

        private String username;
        private String password;

        private List<String> username2;
        private String[] username3;

        public String[] getUsername3() {
            return username3;
        }

        public User setUsername3(String[] username3) {
            this.username3 = username3;
            return this;
        }

        public List<String> getUsername2() {
            return username2;
        }

        public User setUsername2(List<String> username2) {
            this.username2 = username2;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public User setUsername(String username) {
            this.username = username;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public User setPassword(String password) {
            this.password = password;
            return this;
        }
    }
}
