package com.example.frontend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry){
        String dir = "user-photos";
        Path path = Paths.get(dir);
        String avatar = path.toFile().getAbsolutePath();
        resourceHandlerRegistry.addResourceHandler("/"+dir+"/**").addResourceLocations("file:/" + avatar + "/");

    }
}
