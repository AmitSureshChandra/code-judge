package com.github.amitsureshchandra.codejudge.service;

import com.github.amitsureshchandra.codejudge.dto.question.QuestionDetailDto;
import com.github.amitsureshchandra.codejudge.dto.question.QuestionListDto;
import com.github.amitsureshchandra.codejudge.entity.Question;
import com.github.amitsureshchandra.codejudge.repo.QuestionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    final ModelMapper modelMapper;

    final QuestionRepo questionRepo;

    final UserService userService;

    public QuestionService(ModelMapper modelMapper, QuestionRepo questionRepo, UserService userService) {
        this.modelMapper = modelMapper;
        this.questionRepo = questionRepo;
        this.userService = userService;
    }

    public List<QuestionListDto> fetchAllQuestions(Optional<String> search) {
        // search adding is pending
        return questionRepo.findAll().stream().map(q -> modelMapper.map(q, QuestionListDto.class)).collect(Collectors.toList());
    }

    public Question findByCode(String code) {
        return questionRepo.findByCode(code).orElseThrow(() -> new RuntimeException("question not exists"));
    }

    public QuestionDetailDto fetchByCode(String code) {
        return modelMapper.map(findByCode(code), QuestionDetailDto.class);
    }
}
