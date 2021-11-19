package com.github.yt.web.test;

import com.github.yt.web.unittest.EnableYtWebTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
//@EnableSwagger2
@EnableYtWebTest
//@AutoConfigureMockMvc
@Controller
public class YtWebTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(YtWebTestApplication.class, args);
    }

    @GetMapping(path = "/")
    public String index() {
        return "redirect:swagger-ui.html";
    }

}
