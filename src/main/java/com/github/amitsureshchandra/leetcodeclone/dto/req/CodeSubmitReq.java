package com.github.amitsureshchandra.leetcodeclone.dto.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CodeSubmitReq {
    private String code;
    private String compiler;
}
