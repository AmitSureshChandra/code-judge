package com.github.amitsureshchandra.leetcodeclone.repo;

import com.github.amitsureshchandra.leetcodeclone.dto.core.TestCase;
import com.github.amitsureshchandra.leetcodeclone.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepo extends JpaRepository<Question, Long> {
//    @Query("select q.testCases from Question q JOIN q.testCases testCase where q.code = :code and testCase.sample = false")
//    List<TestCase> findSubmissionTestCasesByCode(String code);

    Optional<Question> findByCode(String code);
}
