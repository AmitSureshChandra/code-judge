package com.github.amitsureshchandra.codejudge.entity;

import com.github.amitsureshchandra.codejudge.dto.core.SampleCode;
import com.github.amitsureshchandra.codejudge.dto.core.Example;
import com.github.amitsureshchandra.codejudge.dto.core.TestCase;
import com.github.amitsureshchandra.codejudge.enums.Difficulty;
import com.github.amitsureshchandra.codejudge.enums.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String code;
    private String description;
    private Difficulty difficulty;
    @ElementCollection
    private List<Example> examples;
    @ElementCollection
    private List<Tag> tags;
    @ElementCollection
    private List<String> constraints = new ArrayList<>();
    @ElementCollection
    private List<TestCase> testCases = new ArrayList<>();

    @ElementCollection
    private List<SampleCode> sampleCodes = new ArrayList<>();
}
