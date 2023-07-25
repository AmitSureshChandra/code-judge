package com.github.amitsureshchandra.leetcodeclone.repo;

import com.github.amitsureshchandra.leetcodeclone.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, Long> {
}
