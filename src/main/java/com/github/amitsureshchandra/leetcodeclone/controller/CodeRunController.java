package com.github.amitsureshchandra.leetcodeclone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.amitsureshchandra.leetcodeclone.dto.req.CodeRunReq;
import com.github.amitsureshchandra.leetcodeclone.service.CodeRunService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/run/questions")
public class CodeRunController {

    final CodeRunService codeRunService;

    public CodeRunController(CodeRunService codeRunService) {
        this.codeRunService = codeRunService;
    }

    @PostMapping("/{code}")
    Object run(@PathVariable String code, @RequestBody @Valid CodeRunReq dto, @RequestHeader(name = "Authorization") UUID token) throws IOException {
        return codeRunService.run(code, dto, token);
    }
}
