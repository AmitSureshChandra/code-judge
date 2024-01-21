package com.github.amitsureshchandra.codejudge.controller;

import com.github.amitsureshchandra.codejudge.dto.req.CodeRunReq;
import com.github.amitsureshchandra.codejudge.dto.resp.MsgResponseDto;
import com.github.amitsureshchandra.codejudge.service.AsyncRunner;
import com.github.amitsureshchandra.codejudge.service.CodeRunService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/run/questions")
public class CodeRunController {

    final CodeRunService codeRunService;
    final AsyncRunner asyncRunner;

    public CodeRunController(CodeRunService codeRunService, AsyncRunner asyncRunner) {
        this.codeRunService = codeRunService;
        this.asyncRunner = asyncRunner;
    }

    @PostMapping("/{code}")
    Object run(@PathVariable String code, @RequestBody @Valid CodeRunReq dto) throws IOException {
        return codeRunService.run(code, dto);
    }

    @PostMapping("/async/{qCode}")
    MsgResponseDto runAsync(@PathVariable String qCode, @RequestBody @Valid CodeRunReq dto) throws IOException {
        return new MsgResponseDto(asyncRunner.run(qCode, dto));
    }
}
