package com.turkcell.phisingproject.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessCheckResult {
    private String url;
    private boolean accessible;
    private int httpStatusCode;
    private LocalDateTime checkedAt;
}

