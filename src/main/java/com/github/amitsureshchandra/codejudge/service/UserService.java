package com.github.amitsureshchandra.codejudge.service;

import com.github.amitsureshchandra.codejudge.entity.User;
import com.github.amitsureshchandra.codejudge.exception.AuthException;
import com.github.amitsureshchandra.codejudge.exception.ValidationException;
import com.github.amitsureshchandra.codejudge.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    final UserRepo userRepo;

    @Value("${secret-token}")
    private String secretToken;

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
        if(token.toString().equals(secretToken)) return;
        if(!isAuthenticated(token)) throw new AuthException("Unauthenticated");
    }
}
