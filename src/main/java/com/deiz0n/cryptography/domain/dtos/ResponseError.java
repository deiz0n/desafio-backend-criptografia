package com.deiz0n.cryptography.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Data
public class ResponseError {

    private HttpStatus status;
    private Integer code;
    private String title;
    private String detail;
    private Instant instant;

}
