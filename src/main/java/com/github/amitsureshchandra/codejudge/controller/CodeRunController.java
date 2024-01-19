package com.github.amitsureshchandra.codejudge.controller;

import com.github.amitsureshchandra.codejudge.dto.req.CodeRunReq;
import com.github.amitsureshchandra.codejudge.service.CodeRunService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
