package com.github.amitsureshchandra.codejudge.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseResp {
    private String testCase;
    private String answer;
    private String expectedAns;
    private boolean status;
}
