package com.github.amitsureshchandra.codejudge.dto.question;

import com.github.amitsureshchandra.codejudge.dto.core.Example;
import com.github.amitsureshchandra.codejudge.dto.core.SampleCode;
import com.github.amitsureshchandra.codejudge.dto.core.TestCase;
import com.github.amitsureshchandra.codejudge.enums.Difficulty;
import com.github.amitsureshchandra.codejudge.enums.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class QuestionDetailDto {
    private Long id;
    private String title;
    private String code;
    private String description;
    private Difficulty difficulty;
    private List<Example> examples;
    private List<Tag> tags;
    private List<String> constraints = new ArrayList<>();
    private List<TestCase> testCases = new ArrayList<>();
    private List<SampleCode> sampleCodes = new ArrayList<>();

}
