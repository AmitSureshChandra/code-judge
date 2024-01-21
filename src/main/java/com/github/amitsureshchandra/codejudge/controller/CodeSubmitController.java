package com.github.amitsureshchandra.codejudge.controller;

import com.github.amitsureshchandra.codejudge.dto.req.CodeSubmitReq;
import com.github.amitsureshchandra.codejudge.service.CodeSubmitService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/submit/questions")
public class CodeSubmitController {
    final CodeSubmitService codeSubmitService;

    public CodeSubmitController(CodeSubmitService codeSubmitService) {
        this.codeSubmitService = codeSubmitService;
    }

    @PostMapping("/{code}")
    Object run(@PathVariable String code, @RequestBody @Valid CodeSubmitReq dto) throws IOException {
        return codeSubmitService.run(code, dto);
    }
}
