package org.example.qrcode_service_.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.qrcode_service_.correctiontypes.Correction;
import org.example.qrcode_service_.typeofqrcode.QrcodeType;

import java.time.LocalDateTime;


@Getter
@Setter
public class QrcodeResponseDto {
    private Long id;
    private String contents;
    private Correction correction;
    private int size;
    private QrcodeType type;
    private LocalDateTime createdAt;
}
