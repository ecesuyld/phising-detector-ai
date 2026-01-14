package com.turkcell.phisingproject.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Response<T> {
    private final LocalDateTime time ;
    private int code;
    private String message;
    private T payload;
}
