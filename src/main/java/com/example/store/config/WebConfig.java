package com.example.store.config;

import com.example.store.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/users/**", "/auth/logout", "/courses/**", "/classrooms/**", "/teachers/**", "/students/**", "/documents/**", "/homeworks/**").excludePathPatterns("/auth/login", "/auth/forgot-password", "/auth/reset-password", "/auth/verify-otp");
    }
}
