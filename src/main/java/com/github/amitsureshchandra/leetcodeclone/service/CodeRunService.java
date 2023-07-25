package com.github.amitsureshchandra.leetcodeclone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amitsureshchandra.leetcodeclone.dto.external.CodeReqDto;
import com.github.amitsureshchandra.leetcodeclone.dto.external.OutputResp;
import com.github.amitsureshchandra.leetcodeclone.dto.req.CodeRunReq;
import com.github.amitsureshchandra.leetcodeclone.dto.resp.TestCaseResp;
import com.github.amitsureshchandra.leetcodeclone.service.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CodeRunService {

    @Value("${online-compiler-url}")
    private String onlineCompilerUrl;

    final FileUtil fileUtil;
    final ObjectMapper objectMapper;

    final ResourceLoader resourceLoader;

    final RestTemplate restTemplate;

    public CodeRunService(FileUtil fileUtil, ObjectMapper objectMapper, ResourceLoader resourceLoader, RestTemplate restTemplate) {
        this.fileUtil = fileUtil;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
        this.restTemplate = restTemplate;
    }

    public Object run(String code, CodeRunReq dto) throws IOException {
        // prepare input file

       StringBuilder input = new StringBuilder();

        // first line will be no of test cases
        input.append(dto.getTestCases().size()).append("\n");

        dto.getTestCases().forEach(testCase -> input.append(testCase).append("\n"));

        // prepare code with driver

        System.out.println("path : "+ getRunDriverPath(code, dto.getCompiler()));
        String content = fileUtil.readFileContent(getRunDriverPath(code, dto.getCompiler())) +
                "\n" + dto.getCode();

        // hit api to run code
        OutputResp outputResp = runCodeViaApi(input.toString(), content, dto.getCompiler());

        System.out.println(outputResp);
        // split on \n
        // check if line starts with 'F' then test case is failed

        List<TestCaseResp> resp = new ArrayList<>();
        int pos = 0;

        for (String output : outputResp.getOutput().split("\n")) {
            String[] ansStr = output.split(":")[1].split("~");
            resp.add(new TestCaseResp(
                    dto.getTestCases().get(pos++),
                    ansStr[0],
                    ansStr[1],
                    output.charAt(0) == 'P'
            ));
        }

        return resp;
    }

    private OutputResp runCodeViaApi(String input, String content, String compiler) throws JsonProcessingException {
        // Define the request body
        CodeReqDto dto = new CodeReqDto();
        dto.setCode(content);
        dto.setInput(input);
        dto.setCompiler(compiler);

        System.out.println(dto);

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

    public String getRunDriverPath(String code, String compiler) throws IOException {
        return Objects.requireNonNull(resourceLoader.getResource("classpath:drivers\\lang\\" + compiler + "\\" + code + "\\RunDriver.java")).getFile().getPath();
    }
}
