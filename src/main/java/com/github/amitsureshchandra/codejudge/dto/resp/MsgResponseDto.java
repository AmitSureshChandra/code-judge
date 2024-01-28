package com.github.amitsureshchandra.codejudge.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgResponseDto {
    @JsonProperty("msg")
    String msg;
}
