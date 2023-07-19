package com.sirius.spurt.common.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public CustomFilter customFilter() {
        return new CustomFilter();
    }

    @Bean
    public FilterRegistrationBean<CustomFilter> myCustomFilterRegistration() {
        FilterRegistrationBean<CustomFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(customFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
