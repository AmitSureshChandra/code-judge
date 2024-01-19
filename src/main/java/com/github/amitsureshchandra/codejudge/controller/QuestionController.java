package com.github.amitsureshchandra.codejudge.controller;

import com.github.amitsureshchandra.codejudge.dto.question.QuestionDetailDto;
import com.github.amitsureshchandra.codejudge.dto.question.QuestionListDto;
import com.github.amitsureshchandra.codejudge.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    List<QuestionListDto> questionsList(Optional<String> search) {
        return questionService.fetchAllQuestions(search);
    }

    @GetMapping("/{code}")
    QuestionDetailDto questionDetail(@PathVariable String code) {
        return questionService.fetchByCode(code);
    }
}
