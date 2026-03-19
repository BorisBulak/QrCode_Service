package org.example.qrcode_service_.entity;

import jakarta.persistence.*;
import org.example.qrcode_service_.correctiontypes.Correction;
import org.example.qrcode_service_.typeofqrcode.QrcodeType;

import java.time.LocalDateTime;

@jakarta.persistence.Entity
@Table(name = "qrcodes")
public class Entity {
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

    public QrcodeType   getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public Correction getCorrection() {
        return correction;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCorrection(Correction correction) {
        this.correction = correction;
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
}
