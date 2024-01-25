package com.github.amitsureshchandra.leetcodeclone.dto.req.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LoginReq {
    @NotNull
    @Email
    private String username;

    @NotNull @NotEmpty
    private String password;
}
