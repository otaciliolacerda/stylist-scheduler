package com.otacilio.challange.stylistscheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class SwaggerDocsController {

    @GetMapping("/")
    public String home() {
        return "redirect:swagger-ui.html";
    }
}
