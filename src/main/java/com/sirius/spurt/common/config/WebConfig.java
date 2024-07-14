package com.sirius.spurt.common.config;

import com.sirius.spurt.common.jwt.JwtUtils;
import com.sirius.spurt.common.resolver.LoginUserResolver;
import com.sirius.spurt.common.resolver.NonLoginUserResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtUtils jwtUtils;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserResolver(jwtUtils));
        resolvers.add(new NonLoginUserResolver(jwtUtils));
    }
}
