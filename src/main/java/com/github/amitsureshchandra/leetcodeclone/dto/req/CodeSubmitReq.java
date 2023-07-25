package com.github.amitsureshchandra.leetcodeclone.dto.req;

import com.github.amitsureshchandra.leetcodeclone.dto.core.TestCase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CodeSubmitReq {
    private String input;
    private String code;
    private String compiler;
}
