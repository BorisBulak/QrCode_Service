package org.example.qrcode_service_.enums;

import lombok.Getter;

@Getter
public enum QrcodeType {
    GIF("GIF"),
    PNG("PNG"),
    JPEG("JPEG"),
    ;

    private final String name;

    QrcodeType(String name) {
        this.name = name;
    }
}
