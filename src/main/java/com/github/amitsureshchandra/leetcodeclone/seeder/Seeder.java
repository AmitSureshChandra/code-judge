package com.github.amitsureshchandra.leetcodeclone.seeder;

import com.github.amitsureshchandra.leetcodeclone.dto.core.Example;
import com.github.amitsureshchandra.leetcodeclone.dto.core.SampleCode;
import com.github.amitsureshchandra.leetcodeclone.dto.core.TestCase;
import com.github.amitsureshchandra.leetcodeclone.entity.Question;
import com.github.amitsureshchandra.leetcodeclone.enums.Difficulty;
import com.github.amitsureshchandra.leetcodeclone.enums.Tag;
import com.github.amitsureshchandra.leetcodeclone.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class Seeder {

    final QuestionRepo questionRepo;

    @Value("${run-seeder}")
    private boolean runSeeder;

    public Seeder(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    @PostConstruct
    void init() {
        if(runSeeder) {
            seed();
        }
    }

    private void seed() {
        seedQuestions();
    }

    private void seedQuestions() {
        seedAddTwoNumber();
        seedFactorialQuestion();
    }

    private void seedAddTwoNumber() {
        Question q = new Question();

        q.setTitle("addition of 2 numbers");
        q.setCode("add-two-no");
        q.setDescription("Addition of two numbers");
        q.setTags(Arrays.asList(Tag.ARITHMETIC, Tag.MATH));
        q.setDifficulty(Difficulty.BASIC);
        q.setExamples(
                Arrays.asList(
                        new Example("10\n20", "30", "addition of 10 & 20 is 30"),
                        new Example("10\n0", "10", "addition of 10 & 0 is 10")
                )
        );

        q.setConstraints(
                Arrays.asList(
                        "-2^31 <= n1 <= 2^31-1",
                        "-2^31 <= n2 <= 2^31-1"
                )
        );

        q.setTestCases(
                Arrays.asList(
                        new TestCase("10\n20", false, "30"),
                        new TestCase("-1\n20", false, "19"),
                        new TestCase("10\n-1", false, "9"),
                        new TestCase("-10\n-1", false, "-11")
                )
        );

        q.setSampleCodes(Arrays.asList(
                new SampleCode(
                    "class Problem  { \nint add(int n1, int n2) { \n// write code here \n } \n} ",
                    "jdk8"
                )
        ));

        questionRepo.save(q);
    }

    private void seedFactorialQuestion() {
        // factorial question

        Question q = new Question();

        q.setTitle("factorial of number");
        q.setCode("factorial");
        q.setDescription("factorial of number");
        q.setTags(Arrays.asList(Tag.ARITHMETIC, Tag.MATH));
        q.setDifficulty(Difficulty.BASIC);
        q.setExamples(
                Arrays.asList(
                        new Example("5", "120", "factorial of 5 is 120"),
                        new Example("3", "6", "factorial of 3 is 6")
                )
        );

        q.setConstraints(
                Arrays.asList(
                        "0 <= n1 <= 10"
                )
        );

        q.setTestCases(
                Arrays.asList(
                        new TestCase("0", false, "1"),
                        new TestCase("1", false, "1"),
                        new TestCase("6", false, "720"),
                        new TestCase("5", false, "120")
                )
        );


        q.setSampleCodes(Arrays.asList(
                new SampleCode(
                        "class Problem  { \nlong factorial(int n) { \n// write code here \n } \n} ",
                        "jdk8"
                )
        ));
        questionRepo.save(q);
    }
}
