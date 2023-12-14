package com.dev.yggdrasil.repos;

import com.dev.yggdrasil.domain.Authority;
import com.dev.yggdrasil.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
