package com.rehe.starter.admin;

import com.rehe.database.admin.mapper.order.UserTestMapper33;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

//@EnableAsync
@SpringBootApplication
@ComponentScan({"com.rehe"})
//@MapperScan(value = "com.rehe")
@RequiredArgsConstructor
public class ReheStarterAdminApplication {
    private final UserTestMapper33 userTestMapper33;
    public static void main(String[] args) {
        SpringApplication.run(ReheStarterAdminApplication.class, args);
    }

}
