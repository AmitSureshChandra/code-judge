package com.github.amitsureshchandra.codejudge.service.util;

import com.github.amitsureshchandra.codejudge.exception.AuthException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthUtil {
    public UUID getAccessToken(ServletServerHttpRequest request) {
        if(!request.getHeaders().containsKey("Authorization")) {
            throw new AuthException("Unauthenticated");
        }

        String authToken = request.getHeaders().getFirst("Authorization");
        try {
            return UUID.fromString(authToken);
        }
        catch (IllegalArgumentException exception) {
            throw new AuthException("Unauthenticated");
        }
    }
}
