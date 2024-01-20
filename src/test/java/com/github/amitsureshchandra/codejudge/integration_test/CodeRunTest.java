package com.github.amitsureshchandra.codejudge.integration_test;

import com.github.amitsureshchandra.codejudge.util.BaseTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class CodeRunTest extends BaseTestCase {

    // make sure online-compiler listener is running

    @Autowired
    RestTemplate restTemplate;

    @Test
    void test_flow() {
        // To Do
    }
}
