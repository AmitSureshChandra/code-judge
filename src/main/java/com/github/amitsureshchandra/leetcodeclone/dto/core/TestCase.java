package com.github.amitsureshchandra.leetcodeclone.dto.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class TestCase {
    private String input;
    private boolean sample;
    private String ans;
}
