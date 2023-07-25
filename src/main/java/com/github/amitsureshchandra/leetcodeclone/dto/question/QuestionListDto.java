package com.github.amitsureshchandra.leetcodeclone.dto.question;

import com.github.amitsureshchandra.leetcodeclone.dto.core.Example;
import com.github.amitsureshchandra.leetcodeclone.dto.core.TestCase;
import com.github.amitsureshchandra.leetcodeclone.enums.Difficulty;
import com.github.amitsureshchandra.leetcodeclone.enums.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionListDto {
    private Long id;
    private String title;
    private String code;
    private String description;
    private Difficulty difficulty;
    private List<Example> examples;
    private List<Tag> tags;
    private List<String> constraints = new ArrayList<>();
    private List<TestCase> testCases = new ArrayList<>();
}
