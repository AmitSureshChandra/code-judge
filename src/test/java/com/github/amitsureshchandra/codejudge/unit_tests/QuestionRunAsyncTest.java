package com.github.amitsureshchandra.codejudge.unit_tests;

import com.github.amitsureshchandra.codejudge.dto.req.CodeRunReq;
import com.github.amitsureshchandra.codejudge.service.util.ParseUtil;
import com.github.amitsureshchandra.codejudge.util.BaseControllerTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("mq_test")
public class QuestionRunAsyncTest extends BaseControllerTest {

    @Value("${secret-token}")
    String secretToken;

    @BeforeAll
    static void beforeAll() {
        onlineCompiler.stop();
    }

    @AfterAll
    static void afterAll() {
        onlineCompiler.start();
    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ParseUtil parseUtil;

    @Value("${mq-code-event-queue}")
    public String queueName = "code-queue";


    @Test
    void test_code_execution_for_async_run() throws Exception {

        CodeRunReq dto = new CodeRunReq(
                "class Problem  {\n" +
                        "    long factorial(int n) {\n" +
                        "        int fact = 1;\n" +
                        "        for(int i=1; i<= n; i++) fact *= i;\n" +
                        "        return fact;\n" +
                        "    }\n" +
                        "} ",
                "jdk",
                Arrays.asList("0", "1", "6", "5")
        );


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/run/questions/async/factorial")
                        .content(parseUtil.parseToString(dto))
                        .contentType("application/json")
                        .header("Authorization", secretToken)
                        .accept("application/json")
                )
                .andDo(print())
                .andExpect(status().isOk());

        Thread.sleep(1000 * 5);

        // assert event queued in mq
        Message msg = rabbitTemplate.receive(queueName);
        assertNotNull(msg.getBody());
    }
}
