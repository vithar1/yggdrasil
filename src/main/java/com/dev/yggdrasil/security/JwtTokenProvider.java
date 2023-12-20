package com.dev.yggdrasil.security;

import com.dev.yggdrasil.domain.User;
import com.dev.yggdrasil.service.impl.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserService userService;

    public String generateJwtToken() {
        String secretKey = "2dae84f846e4f4b158a8d26681707f4338495bc7ab68151d7f7679cc5e56202dd3da0d356da007a7c28cb0b780418f4f3246769972d6feaa8f610c7d1e7ecf6a";
//        if (!(authentication.getPrincipal() instanceof UserDetails)) {
//            return "anonymousUser";
//        }
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return "anonymousUser";
        }
        return Jwts.builder()
                .setSubject(currentUser.getUsername())
                .setId(currentUser.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 часа
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}