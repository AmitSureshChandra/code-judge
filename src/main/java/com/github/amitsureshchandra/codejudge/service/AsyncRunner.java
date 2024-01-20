package com.github.amitsureshchandra.codejudge.service;

import com.github.amitsureshchandra.codejudge.config.MQConfig;
import com.github.amitsureshchandra.codejudge.dto.mq.CodeEventDto;
import com.github.amitsureshchandra.codejudge.dto.req.CodeRunReq;
import com.github.amitsureshchandra.codejudge.service.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class AsyncRunner {

    final UserService userService;
    final CodeRunService codeRunService;
    final ParseUtil parseUtil;

    final RabbitTemplate rabbitTemplate;

    public AsyncRunner(UserService userService, CodeRunService codeRunService, ParseUtil parseUtil, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.codeRunService = codeRunService;
        this.parseUtil = parseUtil;
        this.rabbitTemplate = rabbitTemplate;
    }

    public String run(String qCode, CodeRunReq dto, UUID token) throws IOException {
        // check auth
        userService.checkAuth(token);

        UUID codeExcId = UUID.randomUUID();

        String input = codeRunService.prepareInput(dto.getTestCases());
        String sourceCode = codeRunService.prepareCode(qCode, dto.getCompiler(), dto.getCode());

        // add in mq
        addCodeEventInMQ(codeExcId, input, sourceCode, dto.getCompiler());

        return codeExcId.toString();
    }

    private void addCodeEventInMQ(UUID codeExcId, String input, String sourceCode, String compiler) {
        rabbitTemplate.convertAndSend(MQConfig.exchangeName,"code", parseUtil.parseToString(new CodeEventDto(
                codeExcId.toString(),
                sourceCode,
                compiler,
                input
        )));

        log.info("event added in mq {}", new CodeEventDto(
                codeExcId.toString(),
                sourceCode,
                compiler,
                input
        ));
    }
}
