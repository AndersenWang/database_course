package org.example.config;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.RestBean;
import org.example.entity.dto.Account;
import org.example.entity.vo.response.AuthorizeVO;
import org.example.filter.JwtAuthorizedFilter;
import org.example.service.AccountService;
import org.example.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfiguration {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    JwtAuthorizedFilter jwtAuthorizedFilter;
    @Resource
    AccountService service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/**", "/error").permitAll();
                    auth.requestMatchers("/image/**").permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/static/**")).permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(conf -> conf
                        .loginProcessingUrl("/api/auth/login")
                        .failureHandler(this::onFailureHandle)
                        .successHandler(this::onAuthenticationSuccess)
                )
                .logout(conf -> conf
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess)
                )
                .exceptionHandling(conf -> conf
                        .accessDeniedHandler(this::onFailureHandle)
                        .authenticationEntryPoint(this::onFailureHandle)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthorizedFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    public void onFailureHandle(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        final PrintWriter writer = response.getWriter();
        if (exception instanceof AccessDeniedException){
            writer.write(RestBean.failure(403, exception.getMessage()).asJsonString());
        } else if (exception instanceof AuthenticationException) {
            writer.write(RestBean.failure(401, "登录失败，" + exception.getMessage()).asJsonString());
        }
    }
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        User user = (User) authentication.getPrincipal();
        Account account = service.getAccountByUsername(user.getUsername());
        String role = account.getRole();
        String token = jwtUtil.createJwt(account.getUsername(), role);
        AuthorizeVO authorizeVO = new AuthorizeVO();
        BeanUtils.copyProperties(account, authorizeVO);
        authorizeVO.setExpire(jwtUtil.getExpireTime());
        authorizeVO.setToken(token);
        final PrintWriter writer = response.getWriter();

        writer.write(RestBean.success(authorizeVO).asJsonString());
    }
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        final PrintWriter writer = response.getWriter();
        String authorization = request.getHeader("Authorization");
        System.out.println("SecurityConfiguration: " + authorization);
        if (jwtUtil.invalidateJwt(authorization)){
            writer.write(RestBean.success("退出成功").asJsonString());
        }else {
            writer.write(RestBean.failure(400,"退出失败").asJsonString());
        }
    }
}
