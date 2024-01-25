package com.github.amitsureshchandra.leetcodeclone.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class Example {
    private String input;
    private String output;
    private String explanation;
}
