package org.example.qrcode_service_.repository;

import org.example.qrcode_service_.entity.QrCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<QrCodeEntity, Long> {

}
