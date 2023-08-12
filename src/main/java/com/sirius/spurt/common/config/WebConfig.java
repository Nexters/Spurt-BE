package com.sirius.spurt.common.config;

import com.sirius.spurt.common.resolver.LoginUserResolver;
import com.sirius.spurt.common.resolver.NonLoginUserResolver;
import com.sirius.spurt.store.provider.auth.AuthProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthProvider authProvider;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserResolver(authProvider));
        resolvers.add(new NonLoginUserResolver(authProvider));
    }
}
