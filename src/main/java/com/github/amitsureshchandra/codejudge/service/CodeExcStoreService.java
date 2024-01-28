package com.github.amitsureshchandra.codejudge.service;

import com.github.amitsureshchandra.codejudge.dto.external.OutputResp;
import com.github.amitsureshchandra.codejudge.dto.resp.OutputStatusResp;
import com.github.amitsureshchandra.codejudge.enums.OutputStatus;
import com.github.amitsureshchandra.codejudge.service.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CodeExcStoreService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String OUTPUT_KEY = "output";
    final ModelMapper modelMapper;
    final ParseUtil parseUtil;

    final UserService userService;

    public CodeExcStoreService(RedisTemplate<String, Object> redisTemplate, ModelMapper modelMapper, ParseUtil parseUtil, UserService userService) {
        this.redisTemplate = redisTemplate;
        this.modelMapper = modelMapper;
        this.parseUtil = parseUtil;
        this.userService = userService;
    }

    public OutputResp get(String key) {
        Object obj = redisTemplate.opsForHash().get(OUTPUT_KEY, key);
        if(obj == null) return null;
        return parseUtil.parseFromString(obj.toString(), OutputResp.class);
    }

    public boolean checkKeyProcessed(String key) {
        return redisTemplate.opsForHash().hasKey(OUTPUT_KEY, key);
    }

    public OutputStatusResp fetchStatus(String excId) {
        if(!checkKeyProcessed(excId)) {
            // not in redis means under processing
            return new OutputStatusResp(null, null, 0, OutputStatus.PENDING);
        }

        OutputStatusResp outputStatusResp = modelMapper.map(get(excId), OutputStatusResp.class);
        outputStatusResp.setStatus(OutputStatus.COMPLETED);

        return outputStatusResp;
    }
}
