package com.pkh.configuraton;

import com.pkh.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LogInterceptor logInterceptor = new LogInterceptor();
//        registry.addInterceptor(logInterceptor)
//                .addPathPatterns("/**");
    }
}
