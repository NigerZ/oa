package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("org.example.mapper")
public class OAApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAApplication.class, args);
    }
}