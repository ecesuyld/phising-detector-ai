package com.turkcell.phisingproject.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(0),
    ERROR(-999);

    private final int value;
}
