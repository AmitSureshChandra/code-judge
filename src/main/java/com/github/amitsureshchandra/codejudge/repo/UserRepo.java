package com.github.amitsureshchandra.codejudge.repo;

import com.github.amitsureshchandra.codejudge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameAndPassword(String username, String hash);

    Optional<User> findByToken(UUID token);
}
