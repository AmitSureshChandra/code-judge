package com.github.amitsureshchandra.codejudge.unit_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amitsureshchandra.codejudge.dto.req.CodeRunReq;
import com.github.amitsureshchandra.codejudge.service.UserService;
import com.github.amitsureshchandra.codejudge.util.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class QuestionRunTest extends BaseControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test_code_execution_for_wrong_run() throws Exception {
        doNothing().when(userService).checkAuth(any());

        CodeRunReq dto = new CodeRunReq(
                "class Problem  { \n" +
                        "long factorial(int n) { \n" +
                        "       return 1;\n" +
                        " } \n" +
                        "} ",
                "jdk8",
                Arrays.asList("0", "1", "6", "5")
        );


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/run/questions/factorial")
                        .content(objectMapper.writeValueAsBytes(dto))
                        .contentType("application/json")
                        .header("Authorization", UUID.randomUUID())
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk())

                // for test case 1
                .andExpect(jsonPath("$[0].testCase", is("0")))
                .andExpect(jsonPath("$[0].answer", is("1")))
                .andExpect(jsonPath("$[0].expectedAns", is("1")))
                .andExpect(jsonPath("$[0].status", is(true)))

                // for test case 2
                .andExpect(jsonPath("$[1].testCase", is("1")))
                .andExpect(jsonPath("$[1].answer", is("1")))
                .andExpect(jsonPath("$[1].expectedAns", is("1")))
                .andExpect(jsonPath("$[1].status", is(true)))

                // for test case 3
                .andExpect(jsonPath("$[2].testCase", is("6")))
                .andExpect(jsonPath("$[2].answer", is("1")))
                .andExpect(jsonPath("$[2].expectedAns", is("720")))
                .andExpect(jsonPath("$[2].status", is(false)))

                // for test case 2
                .andExpect(jsonPath("$[3].testCase", is("5")))
                .andExpect(jsonPath("$[3].answer", is("1")))
                .andExpect(jsonPath("$[3].expectedAns", is("120")))
                .andExpect(jsonPath("$[3].status", is(false)));

    }

    @Test
    void test_code_execution_for_correct_run() throws Exception {
        doNothing().when(userService).checkAuth(any());

        CodeRunReq dto = new CodeRunReq(
                "class Problem  {\n" +
                        "    long factorial(int n) {\n" +
                        "        int fact = 1;\n" +
                        "        for(int i=1; i<= n; i++) fact *= i;\n" +
                        "        return fact;\n" +
                        "    }\n" +
                        "} ",
                "jdk8",
                Arrays.asList("0", "1", "6", "5")
        );


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/run/questions/factorial")
                        .content(objectMapper.writeValueAsBytes(dto))
                        .contentType("application/json")
                        .header("Authorization", UUID.randomUUID())
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk())

                // for test case 1
                .andExpect(jsonPath("$[0].testCase", is("0")))
                .andExpect(jsonPath("$[0].answer", is("1")))
                .andExpect(jsonPath("$[0].expectedAns", is("1")))
                .andExpect(jsonPath("$[0].status", is(true)))

                // for test case 2
                .andExpect(jsonPath("$[1].testCase", is("1")))
                .andExpect(jsonPath("$[1].answer", is("1")))
                .andExpect(jsonPath("$[1].expectedAns", is("1")))
                .andExpect(jsonPath("$[1].status", is(true)))

                // for test case 3
                .andExpect(jsonPath("$[2].testCase", is("6")))
                .andExpect(jsonPath("$[2].answer", is("720")))
                .andExpect(jsonPath("$[2].expectedAns", is("720")))
                .andExpect(jsonPath("$[2].status", is(true)))

                // for test case 2
                .andExpect(jsonPath("$[3].testCase", is("5")))
                .andExpect(jsonPath("$[3].answer", is("120")))
                .andExpect(jsonPath("$[3].expectedAns", is("120")))
                .andExpect(jsonPath("$[3].status", is(true)));

    }
}

class Problem  {
    long factorial(int n) {
        int fact = 1;
        for(int i=1; i<= n; i++) fact *= i;
        return fact;
    }
}