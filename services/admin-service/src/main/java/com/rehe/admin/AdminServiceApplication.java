package com.rehe.admin;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description
 * @author rehe
 * @date 2024/8/29
 */
@SpringBootApplication
@ComponentScan({"com.rehe"})
@EnableAsync
public class AdminServiceApplication {
    private static final Logger log = LoggerFactory.getLogger(AdminServiceApplication.class);
    static {
//        String rootPath = System.getProperty("user.dir");
//        String pathSeparator = File.separator;
//        System.setProperty("ROOT_PATH", rootPath);
//        System.setProperty("ROOT_SEPARATOR", pathSeparator);
    }
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(AdminServiceApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        String name = env.getProperty("spring.application.name");
        name = !StringUtils.hasLength(name) ? "Application" : name;
        path = !StringUtils.hasLength(path) ? "" : path;
        log.info("\n----------------------------------------------------------\n\t{} is running! Access URLs:\n\t本地访问地址: \thttp://localhost:{}{}/\n\tIP访问地址: \thttp://{}:{}{}/\n\tAPI接口文档地址: \thttp://{}:{}{}/doc.html\n----------------------------------------------------------", name, port, path, ip, port, path, ip, port, path);
    }

}
