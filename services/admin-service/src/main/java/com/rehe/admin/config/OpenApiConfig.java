package com.rehe.admin.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;

import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
            .info(new Info()
                .title("admin api 1.0")
                .description("admin api 1.0")
                .contact(new Contact().name("xiech ").email("1@.com").url("www"))
                .version("v1.0"));
    }

    @Bean
    public GroupedOpenApi adminApi(){
        String[] paths = { "/**" };
        String[] packagedToMatch = { "com.rehe" };
        return GroupedOpenApi.builder().group("后台管理1.0")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(openApi -> {

                })
                .addOperationCustomizer((operation, handlerMethod) -> {
                    // OperationId 作为排序字段
                    operation.addExtension("x-order",operation.getOperationId());
                    // 增加全局header参数
                    return operation.addParametersItem(
                            new HeaderParameter().name("Authorization").example("登录token").description("登录token")
                                    .schema(new StringSchema()._default("Bearer ").name("Authorization").description("登录token")));
                })
                .packagesToScan(packagedToMatch).build();
    }


}
