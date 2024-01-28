package com.github.amitsureshchandra.codejudge.service.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class HashUtil {
    public String hash(String key) {
        return DigestUtils.sha256Hex(key);
    }

    public boolean isMatchingHash(String hash, String key) {
        return hash.equals(hash(key));
    }
}
