package com.fairfood.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security配置类
 * 配置JWT认证和授权规则
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 配置安全过滤链
     * - 禁用CSRF
     * - 禁用Session管理(JWT无状态认证)
     * - 允许所有API请求
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 允许Swagger和API文档路径
                        .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-ui.html").permitAll()
                        // 允许健康检查
                        .requestMatchers("/actuator/health").permitAll()
                        // 允许所有其他请求
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    /**
     * 密码编码器Bean
     * 使用BCrypt加密算法
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
