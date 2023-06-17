package com.james.initializr.annotation;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

/*    *//**
     * 把自己定义的方法拦截器注入进来
     * @return JsonSchemaInterceptor
     *//*
    @Bean
    public HandlerInterceptor getSchemaInterceptor() {
        return new JsonSchemaInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSchemaInterceptor());
    }*/


}
