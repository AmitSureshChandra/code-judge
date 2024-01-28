package com.github.amitsureshchandra.codejudge.config;

import com.github.amitsureshchandra.codejudge.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${cors-origins}")
    String corsOrigin;

    final UserService userService;

    public WebConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                boolean check = false;
                try {
                    check = userService.isAuthenticated(UUID.fromString(request.getHeader("Authorization")));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (!check) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                return HandlerInterceptor.super.preHandle(request, response, handler) && check;
            }
        });
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsOrigin.split(",")) // Allow requests from example.com
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(false); // Allow credentials (e.g., cookies)
    }
}
