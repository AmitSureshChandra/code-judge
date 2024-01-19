package com.github.amitsureshchandra.codejudge.service.auth;

import com.github.amitsureshchandra.codejudge.dto.req.auth.LoginReq;
import com.github.amitsureshchandra.codejudge.dto.req.auth.RegReq;
import com.github.amitsureshchandra.codejudge.dto.resp.auth.LoginResp;
import com.github.amitsureshchandra.codejudge.dto.resp.auth.RegResp;
import com.github.amitsureshchandra.codejudge.entity.User;
import com.github.amitsureshchandra.codejudge.exception.AuthException;
import com.github.amitsureshchandra.codejudge.repo.UserRepo;
import com.github.amitsureshchandra.codejudge.service.UserService;
import com.github.amitsureshchandra.codejudge.service.util.AuthUtil;
import com.github.amitsureshchandra.codejudge.service.util.HashUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    final ModelMapper modelMapper;
    final HashUtil hashUtil;
    final UserRepo userRepo;

    final UserService userService;

    final AuthUtil authUtil;

    public AuthService(ModelMapper modelMapper, HashUtil hashUtil, UserRepo userRepo, UserService userService, AuthUtil authUtil) {
        this.modelMapper = modelMapper;
        this.hashUtil = hashUtil;
        this.userRepo = userRepo;
        this.userService = userService;
        this.authUtil = authUtil;
    }

    public RegResp register(RegReq regReq) {
        User user = modelMapper.map(regReq, User.class);
        user.setPassword(hashUtil.hash(user.getPassword()));
        user.setToken(UUID.randomUUID());
        userRepo.save(user);
        return new RegResp(user.getToken().toString());
    }

    public LoginResp login(LoginReq loginReq) {
        Optional<User> userOptional = userRepo.findByUsernameAndPassword(loginReq.getUsername(), hashUtil.hash(loginReq.getPassword()));
        if(!userOptional.isPresent()) throw new AuthException("Unauthenticated");
        User user = userOptional.get();
        user.setToken(UUID.randomUUID());
        System.out.println(user);
        userRepo.save(user);
        return new LoginResp(user.getToken().toString());
    }

    public void logout(UUID token) {
        Optional<User> optionalUser = userRepo.findByToken(token);
        if(!optionalUser.isPresent()) throw new AuthException("Unauthenticated");

        User user = optionalUser.get();

        user.setToken(null);

        userRepo.save(user);
    }
}
