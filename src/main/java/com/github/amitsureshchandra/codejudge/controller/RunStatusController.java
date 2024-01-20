package com.github.amitsureshchandra.codejudge.controller;

import com.github.amitsureshchandra.codejudge.dto.resp.OutputStatusResp;
import com.github.amitsureshchandra.codejudge.service.CodeExcStoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/status")
@RestController
public class RunStatusController {
    final CodeExcStoreService codeExcStoreService;

    public RunStatusController(CodeExcStoreService codeExcStoreService) {
        this.codeExcStoreService = codeExcStoreService;
    }

    @GetMapping("/{excId}")
    OutputStatusResp fetchStatus(@PathVariable String excId) {
        return codeExcStoreService.fetchStatus(excId);
    }
}
