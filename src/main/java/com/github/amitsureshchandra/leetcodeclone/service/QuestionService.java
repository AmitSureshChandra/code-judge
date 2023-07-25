package com.github.amitsureshchandra.leetcodeclone.service;

import com.github.amitsureshchandra.leetcodeclone.dto.question.QuestionListDto;
import com.github.amitsureshchandra.leetcodeclone.repo.QuestionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    final ModelMapper modelMapper;

    final QuestionRepo questionRepo;

    public QuestionService(ModelMapper modelMapper, QuestionRepo questionRepo) {
        this.modelMapper = modelMapper;
        this.questionRepo = questionRepo;
    }

    public List<QuestionListDto> fetchAllQuestions(Optional<String> search) {
        System.out.println(questionRepo.findAll());
        // search adding is pending
        return questionRepo.findAll().stream().map(q -> modelMapper.map(q, QuestionListDto.class)).collect(Collectors.toList());
    }
}
