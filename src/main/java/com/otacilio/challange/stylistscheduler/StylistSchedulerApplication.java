package com.otacilio.challange.stylistscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@SpringBootApplication
public class StylistSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StylistSchedulerApplication.class, args);
    }

    @Bean
    public Docket api() {
        @SuppressWarnings("deprecation")
        ApiInfo apiInfo = new ApiInfo(
                "Stylist Scheduler",
                "Stylist Scheduler API Challange",
                "1.0",
                "https://tos.org",
                "Otacilio Lacerda <otacilio@demail.com>",
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0");

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .apiInfo(apiInfo);
    }

}
