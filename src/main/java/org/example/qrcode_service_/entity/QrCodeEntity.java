package org.example.qrcode_service_.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.qrcode_service_.enums.Correction;
import org.example.qrcode_service_.enums.QrcodeType;

import java.time.LocalDateTime;

@Getter
@Setter
@jakarta.persistence.Entity
@Table(name = "qrcodes")
public class QrCodeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String contents;
    private int size;

    @Enumerated(EnumType.STRING)
    private Correction correction;

    @Enumerated(EnumType.STRING)
    private QrcodeType type;

    private LocalDateTime createdAt = LocalDateTime.now();
}
