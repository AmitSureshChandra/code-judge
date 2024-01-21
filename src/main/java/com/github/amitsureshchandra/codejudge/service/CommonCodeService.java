package com.github.amitsureshchandra.codejudge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amitsureshchandra.codejudge.dto.external.CodeReqDto;
import com.github.amitsureshchandra.codejudge.dto.external.OutputResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CommonCodeService {

    @Value("${online-compiler-url}")
    private String onlineCompilerUrl;

    final ObjectMapper objectMapper;
    final RestTemplate restTemplate;

    public CommonCodeService(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    public OutputResp runCodeViaApi(String input, String content, String compiler) throws JsonProcessingException {
        // Define the request body
        CodeReqDto dto = new CodeReqDto();
        dto.setCode(content);
        dto.setInput(input);
        dto.setCompiler(compiler);

        // Define HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // Wrap the request body and headers into an HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(dto), headers);

        // Send the POST request
        ResponseEntity<OutputResp> response = restTemplate.exchange(
                onlineCompilerUrl + "/api/v1/run", HttpMethod.POST, entity, OutputResp.class);

        return response.getBody();
    }
}
