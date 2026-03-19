package org.example.qrcode_service_.dto;

import org.example.qrcode_service_.typeofqrcode.QrcodeType;

import java.time.LocalDateTime;

public class QrcodeResponseDto {
    private Long id;
    private String contents;
    private String correction;
    private int size;
    private QrcodeType type;
    private LocalDateTime createdAt;
    private byte[] qrcode;

    public QrcodeType getType() {
        return type;
    }

    public String getCorrection() {
        return correction;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getSize() {
        return size;
    }

    public Long getId() {
        return id;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(QrcodeType type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getQrcode() {
        return qrcode;
    }

    public void setQrcode(byte[] qrcode) {
        this.qrcode = qrcode;
    }
}
