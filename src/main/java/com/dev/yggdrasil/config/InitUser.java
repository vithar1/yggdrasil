package com.dev.yggdrasil.config;

import com.dev.yggdrasil.domain.Authority;
import com.dev.yggdrasil.model.dto.UserDTO;
import com.dev.yggdrasil.repos.AuthorityRepository;
import com.dev.yggdrasil.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class InitUser implements CommandLineRunner{
    private final UserService userService;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Authority authority = new Authority();
        authority.setName("ROLE_USER");
        authority = authorityRepository.save(authority);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("user@gmail.com");
        userDTO.setUsername("user");
        userDTO.setName("user");
        userDTO.setPassword(passwordEncoder.encode("user"));
        userDTO.setAuthorities(List.of(authority.getId()));
        userService.create(userDTO);
    }
}
