package com.github.amitsureshchandra.leetcodeclone.controller;

import com.github.amitsureshchandra.leetcodeclone.dto.req.auth.LoginReq;
import com.github.amitsureshchandra.leetcodeclone.dto.req.auth.RegReq;
import com.github.amitsureshchandra.leetcodeclone.dto.resp.auth.LoginResp;
import com.github.amitsureshchandra.leetcodeclone.dto.resp.auth.RegResp;
import com.github.amitsureshchandra.leetcodeclone.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    RegResp register(@RequestBody RegReq regReq) {
        return authService.register(regReq);
    }

    @PostMapping("/login")
    LoginResp login(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }

    @PostMapping("/logout")
    void logout(@RequestHeader(name = "Authorization") UUID token) {
        authService.logout(token);
    }
}
