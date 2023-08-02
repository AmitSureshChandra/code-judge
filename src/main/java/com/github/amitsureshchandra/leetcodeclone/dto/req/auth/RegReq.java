package com.github.amitsureshchandra.leetcodeclone.dto.req.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class RegReq {
    @NotNull @Email
    private String username;

    @NotNull @Email
    private String email;

    @NotNull @NotEmpty
    private String password;
}
