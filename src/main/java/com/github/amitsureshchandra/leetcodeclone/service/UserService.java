package com.github.amitsureshchandra.leetcodeclone.service;

import com.github.amitsureshchandra.leetcodeclone.entity.User;
import com.github.amitsureshchandra.leetcodeclone.exception.AuthException;
import com.github.amitsureshchandra.leetcodeclone.exception.ValidationException;
import com.github.amitsureshchandra.leetcodeclone.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new ValidationException("user not found"));
    }

    public boolean isAuthenticated(UUID token) {
        Optional<User> optionalUser = userRepo.findByToken(token);
        return optionalUser.isPresent();
    }

    public void checkAuth(UUID token) {
        if(!isAuthenticated(token)) throw new AuthException("Unauthenticated");
    }
}
