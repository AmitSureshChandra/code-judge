package com.github.amitsureshchandra.leetcodeclone.controller;

import com.github.amitsureshchandra.leetcodeclone.dto.req.CodeRunReq;
import com.github.amitsureshchandra.leetcodeclone.dto.req.CodeSubmitReq;
import com.github.amitsureshchandra.leetcodeclone.service.CodeSubmitService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.io.IOException;

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
