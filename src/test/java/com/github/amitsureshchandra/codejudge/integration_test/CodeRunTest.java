package com.github.amitsureshchandra.codejudge.integration_test;

import com.github.amitsureshchandra.codejudge.dto.req.CodeRunReq;
import com.github.amitsureshchandra.codejudge.dto.resp.MsgResponseDto;
import com.github.amitsureshchandra.codejudge.dto.resp.OutputStatusResp;
import com.github.amitsureshchandra.codejudge.enums.OutputStatus;
import com.github.amitsureshchandra.codejudge.service.util.ParseUtil;
import com.github.amitsureshchandra.codejudge.util.BaseTestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@ActiveProfiles("mq_test")
public class CodeRunTest extends BaseTestCase {

    @LocalServerPort
    int port;

    @Value("${secret-token}")
    String secret;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ParseUtil parseUtil;
    @Test
    void test_flow() throws InterruptedException {
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", secret); // Add any other headers as needed

        HttpEntity<CodeRunReq> requestEntity = new HttpEntity<>(dto, headers);

        MsgResponseDto msgResponseDto = restTemplate.postForEntity(
                getUrl("/api/v1/run/questions/async/factorial"),
                requestEntity,
                MsgResponseDto.class,
                new HashMap<>()
        ).getBody();

        String excId = msgResponseDto.getMsg();
        log.info("excId : {}", excId);

        OutputStatusResp outputStatusResp = restTemplate.exchange(getUrl("/api/v1/status/" + excId), HttpMethod.GET, new HttpEntity<>(null, headers), OutputStatusResp.class, new HashMap<>()).getBody();

        int attemps = 10;
        while (Arrays.asList(OutputStatus.PENDING, OutputStatus.SUBMITTED).contains(outputStatusResp.getStatus())) {
            if(attemps <= 0) {
                assert false;
            }
            Thread.sleep(1000); // 1s sleep for short poll
            outputStatusResp = restTemplate.exchange(getUrl("/api/v1/status/" + excId), HttpMethod.GET, new HttpEntity<>(null, headers), OutputStatusResp.class, new HashMap<>()).getBody();
            attemps--;
        }

        assertEquals(OutputStatus.COMPLETED, outputStatusResp.getStatus());
        assertNotNull(outputStatusResp.getOutput());
        System.out.println(outputStatusResp.getOutput());
    }

    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }

}
