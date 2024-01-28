package com.github.amitsureshchandra.codejudge.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessageDto {
    String message;
    int statusCode;
}