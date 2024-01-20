package com.github.amitsureshchandra.codejudge.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class BaseControllerTest extends BaseTestCase {

    @Autowired
    protected MockMvc mockMvc;
}
