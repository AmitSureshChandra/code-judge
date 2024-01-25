package com.github.amitsureshchandra.leetcodeclone.dto.external;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
