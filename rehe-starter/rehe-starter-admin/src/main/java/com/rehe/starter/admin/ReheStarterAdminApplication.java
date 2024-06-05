package com.rehe.starter.admin;

import com.rehe.modules.admin.system.mapper.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan({"com.rehe"})
public class ReheStarterAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReheStarterAdminApplication.class, args);
    }

}
