package com.example.apigateway.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class ErrorDTO {

    private final String message;
   // private final LocalDate date = LocalDate.now();
}
