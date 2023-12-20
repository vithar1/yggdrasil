package com.dev.yggdrasil.config;

import com.dev.yggdrasil.repos.UserRepository;
import com.dev.yggdrasil.security.JwtTokenProvider;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header(
                "Authorization",
                jwtTokenProvider.generateJwtToken()
        );
    }
}
