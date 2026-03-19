package org.example.qrcode_service_.repository;

import org.example.qrcode_service_.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QrCodeRepository extends JpaRepository<Entity,Long> {

}
