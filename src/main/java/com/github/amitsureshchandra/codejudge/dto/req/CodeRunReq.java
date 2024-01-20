package com.github.amitsureshchandra.codejudge.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeRunReq {
    private String code;
    private String compiler;
    private List<String> testCases;
}
