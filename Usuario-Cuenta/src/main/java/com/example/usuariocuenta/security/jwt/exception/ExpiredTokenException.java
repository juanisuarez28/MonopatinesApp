package com.example.usuariocuenta.security.jwt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExpiredTokenException extends RuntimeException {

    private final EnumJWTException code;
    private final String message;

}