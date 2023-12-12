package com.dev.yggdrasil.service;

import com.dev.yggdrasil.domain.Authority;
import com.dev.yggdrasil.domain.User;
import com.dev.yggdrasil.model.dto.UserDTO;
import com.dev.yggdrasil.repos.AuthorityRepository;
import com.dev.yggdrasil.repos.UserRepository;
import com.dev.yggdrasil.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public UserService(final UserRepository userRepository,
            final AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAuthorities(user.getAuthorities().stream()
                .map(authority -> authority.getId())
                .toList());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        final List<Authority> authorities = authorityRepository.findAllById(
                userDTO.getAuthorities() == null ? Collections.emptyList() : userDTO.getAuthorities());
        if (authorities.size() != (userDTO.getAuthorities() == null ? 0 : userDTO.getAuthorities().size())) {
            throw new NotFoundException("one of authorities not found");
        }
        user.setAuthorities(authorities.stream().collect(Collectors.toSet()));
        return user;
    }

}
