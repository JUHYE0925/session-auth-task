package com.hashsnap.login.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* 단일 응답값을 감싸는 공통 DTO */
@Getter
@AllArgsConstructor
public class SingleResponseDTO<T> {

    private T data;
}
