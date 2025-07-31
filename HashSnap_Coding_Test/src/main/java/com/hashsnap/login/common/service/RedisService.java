package com.hashsnap.login.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final StringRedisTemplate stringRedisTemplate;

    // 값 저장 (기한 설정 포함)
    public void setValues(String key, String value, Duration timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout);
    }

    // 값 조회
    public String getValues(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    // 값 존재 여부 확인
    public boolean checkExistsValue(String value) {
        return value != null && !value.isBlank();
    }

    // 키 삭제 (선택적으로 사용)
    public void deleteValues(String key) {
        stringRedisTemplate.delete(key);
    }

}
