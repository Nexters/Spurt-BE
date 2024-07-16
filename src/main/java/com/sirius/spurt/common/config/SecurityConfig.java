package com.sirius.spurt.common.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirius.spurt.common.filter.ExceptionHandlerFilter;
import com.sirius.spurt.common.jwt.AuthorizationFilter;
import com.sirius.spurt.common.jwt.JwtUtils;
import com.sirius.spurt.common.oauth.handler.OAuth2AuthenticationFailHandler;
import com.sirius.spurt.common.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.sirius.spurt.common.oauth.service.OAuth2Service;
import com.sirius.spurt.store.repository.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;
    private final OAuth2Service oAuth2Service;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        (sessionManagement) -> sessionManagement.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests(
                        (requests) ->
                                requests
                                        .requestMatchers("/oauth2/**")
                                        .permitAll()
                                        .requestMatchers("/v1/question/random")
                                        .permitAll()
                                        .requestMatchers("/v1/jobgroup", HttpMethod.POST.name())
                                        .permitAll()
                                        .requestMatchers("/error")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .addFilterBefore(authorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter(), LogoutFilter.class)
                .oauth2Login(
                        oauth2LoginConfigurer ->
                                oauth2LoginConfigurer
                                        .successHandler(oAuth2AuthenticationSuccessHandler())
                                        .failureHandler(oAuth2AuthenticationFailHandler())
                                        .userInfoEndpoint(
                                                userInfoEndpoint -> userInfoEndpoint.userService(oAuth2Service)));

        return http.build();
    }

    @Bean
    public AuthorizationFilter authorizationFilter() {
        return new AuthorizationFilter(userRepository, jwtUtils);
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter(objectMapper);
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(jwtUtils);
    }

    @Bean
    public OAuth2AuthenticationFailHandler oAuth2AuthenticationFailHandler() {
        return new OAuth2AuthenticationFailHandler(objectMapper);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addExposedHeader("RefreshToken");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
