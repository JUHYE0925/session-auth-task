package com.hashsnap.login.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* 이건 이메일 인증 결과(true 또는 false)를 표현하는 응답 객체 (DTO) */
@Getter
@AllArgsConstructor(staticName = "of")
public class EmailVerificationResult {

    private boolean success;
}
