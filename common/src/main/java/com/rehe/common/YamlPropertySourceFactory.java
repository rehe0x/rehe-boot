package com.rehe.common;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author xiech
 * @description
 * @date 2024/1/5
 */

public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {
    @Override
    @NonNull
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        Resource resourceResource = resource.getResource();
        if(!resourceResource.exists()){
            return new PropertiesPropertySource("", new Properties());
        } else if(Objects.requireNonNull(resourceResource.getFilename()).endsWith(".yml") ||
                        resourceResource.getFilename().endsWith(".yaml")){
            List<PropertySource<?>> sources = new YamlPropertySourceLoader()
                    .load(resourceResource.getFilename(),resourceResource);
            return sources.get(0);
        }
        return super.createPropertySource(name, resource);
    }
}
