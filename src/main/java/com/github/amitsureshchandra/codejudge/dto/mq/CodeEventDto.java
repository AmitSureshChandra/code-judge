package com.github.amitsureshchandra.codejudge.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class CodeEventDto {
    private String id;
    private String code;
    private String compiler;
    private String input;
}
