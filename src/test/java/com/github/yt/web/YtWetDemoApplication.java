package com.github.yt.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableYtWeb
@EnableSwagger2
public class YtWetDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(YtWetDemoApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean reg = new ServletRegistrationBean(dispatcherServlet);
        reg.addUrlMappings("/rest/*");
        reg.setName("rest");
        return reg;
    }
}
