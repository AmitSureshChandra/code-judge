package com.github.amitsureshchandra.codejudge.dto.external;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ToString
public class CodeReqDto {

    @NotNull
    @NotBlank
    private String code;

    @NotNull
    @NotBlank
    private String compiler;

    private String input;
}
