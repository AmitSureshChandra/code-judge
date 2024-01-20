package com.github.amitsureshchandra.codejudge.dto.resp;

import com.github.amitsureshchandra.codejudge.enums.OutputStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OutputStatusResp {
    private String output;
    private String error;
    private int exitCode;
    private OutputStatus status;
}
