package com.github.amitsureshchandra.codejudge.service;

import com.github.amitsureshchandra.codejudge.dto.core.TestCase;
import com.github.amitsureshchandra.codejudge.dto.req.CodeSubmitReq;
import com.github.amitsureshchandra.codejudge.entity.Question;
import com.github.amitsureshchandra.codejudge.repo.QuestionRepo;
import com.github.amitsureshchandra.codejudge.service.util.FileUtil;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class CodeSubmitService {

    final QuestionService questionService;
    final CommonCodeService commonCodeService;
    final QuestionRepo questionRepo;

    final FileUtil fileUtil;

    final ResourceLoader resourceLoader;

    public CodeSubmitService(QuestionService questionService, CommonCodeService commonCodeService, QuestionRepo questionRepo, FileUtil fileUtil, ResourceLoader resourceLoader) {
        this.questionService = questionService;
        this.commonCodeService = commonCodeService;
        this.questionRepo = questionRepo;
        this.fileUtil = fileUtil;
        this.resourceLoader = resourceLoader;
    }

    public Object run(String code, CodeSubmitReq dto) throws IOException {
        // prepare input file

        Question q = questionService.findByCode(code);
        List<TestCase> testCases = q.getTestCases();

        StringBuilder input = new StringBuilder();

        // first line will be no of test cases
        input.append(testCases.size()).append("\n");

        testCases.forEach(testCase -> input.append(testCase.getInput()).append("\n").append(testCase.getAns()).append("\n"));

        // prepare code with driver

        String content = fileUtil.readFileContent(getSubmissionDriverPath(code, dto.getCompiler())) +
                "\n" + dto.getCode();

        // hit api to run code
        return commonCodeService.runCodeViaApi(input.toString(), content, dto.getCompiler());
    }

    private String getSubmissionDriverPath(String code, String compiler) throws IOException {
        return Objects.requireNonNull(resourceLoader.getResource("classpath:drivers\\lang\\" + compiler + "\\" + code + "\\SubmitDriver.java")).getFile().getPath();
    }
}
