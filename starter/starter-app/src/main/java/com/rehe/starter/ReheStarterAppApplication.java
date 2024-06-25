package com.rehe.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.rehe"})
public class ReheStarterAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReheStarterAppApplication.class, args);
    }

}
