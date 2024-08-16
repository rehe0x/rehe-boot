package com.rehe.storage.weed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiech
 * @description
 * @date 2024/8/9
 */
@Configuration
public class FilerConfig {
    @Value("${storage.weed.filer.url}")
    private String url;

    @Bean
    public FilerClient filerClient(){
        return FilerClient.builder().filerUrl(url).build();
    }
}
