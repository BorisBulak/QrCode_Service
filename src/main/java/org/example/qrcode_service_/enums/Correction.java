package org.example.qrcode_service_.enums;

import lombok.Getter;

@Getter
public enum Correction {
    L("L"),
    M("M"),
    Q("Q"),
    H("H");

    private final String correction;

    Correction(String correction) {
        this.correction = correction;
    }
}
