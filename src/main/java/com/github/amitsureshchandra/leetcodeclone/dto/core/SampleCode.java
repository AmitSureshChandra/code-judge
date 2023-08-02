package com.github.amitsureshchandra.leetcodeclone.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class SampleCode {
    private String code;
    private String compiler;
}
