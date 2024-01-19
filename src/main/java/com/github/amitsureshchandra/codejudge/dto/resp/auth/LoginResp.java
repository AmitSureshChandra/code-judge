package com.github.amitsureshchandra.codejudge.dto.resp.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResp {
    private String token;
}
