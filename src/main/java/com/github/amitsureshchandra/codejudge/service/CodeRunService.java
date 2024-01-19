package com.github.amitsureshchandra.codejudge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.amitsureshchandra.codejudge.dto.external.OutputResp;
import com.github.amitsureshchandra.codejudge.dto.req.CodeRunReq;
import com.github.amitsureshchandra.codejudge.dto.resp.TestCaseResp;
import com.github.amitsureshchandra.codejudge.service.util.FileUtil;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CodeRunService {

    final CommonCodeService commonCodeService;
    final FileUtil fileUtil;
    final ObjectMapper objectMapper;

    final ResourceLoader resourceLoader;

    final RestTemplate restTemplate;

    final UserService userService;

    public CodeRunService(CommonCodeService commonCodeService, FileUtil fileUtil, ObjectMapper objectMapper, ResourceLoader resourceLoader, RestTemplate restTemplate, UserService userService) {
        this.commonCodeService = commonCodeService;
        this.fileUtil = fileUtil;
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    public Object run(String code, CodeRunReq dto, UUID token) throws IOException {

        // check auth
        userService.checkAuth(token);

        // prepare input file
        StringBuilder input = new StringBuilder();

        // first line will be no of test cases
        input.append(dto.getTestCases().size()).append("\n");

        dto.getTestCases().forEach(testCase -> input.append(testCase).append("\n"));

        // prepare code with driver

        String content = fileUtil.readFileContent(getRunDriverPath(code, dto.getCompiler())) +
                "\n" + dto.getCode();

        // hit api to run code
        OutputResp outputResp = commonCodeService.runCodeViaApi(input.toString(), content, dto.getCompiler());

        // split on \n
        // check if line starts with 'F' then test case is failed

        if(outputResp.getError() != null && !outputResp.getError().isEmpty()) return outputResp;

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

    public String getRunDriverPath(String code, String compiler) throws IOException {
        switch (compiler) {
            case "jdk8":
                return Objects.requireNonNull(resourceLoader.getResource("classpath:drivers\\lang\\" + compiler + "\\" + code + "\\RunDriver.java")).getFile().getPath();
            case "golang12":
                return Objects.requireNonNull(resourceLoader.getResource("classpath:drivers\\lang\\" + compiler + "\\" + code + "\\run_driver.go")).getFile().getPath();
            default:
                throw new RuntimeException("compiler not found");
        }
    }
}
