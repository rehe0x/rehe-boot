package com.rehe.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@ComponentScan({"com.rehe"})
@EnableAsync
@EnableScheduling
public class ScheduledServiceApplication {
    private static final Logger log = LoggerFactory.getLogger(ScheduledServiceApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(ScheduledServiceApplication.class, args);
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
