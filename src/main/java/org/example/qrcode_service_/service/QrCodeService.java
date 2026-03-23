package org.example.qrcode_service_.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.example.qrcode_service_.dto.QrcodeRequestDto;
import org.example.qrcode_service_.dto.QrcodeResponseDto;
import org.example.qrcode_service_.entity.QrCodeEntity;
import org.example.qrcode_service_.mapper.QrCodeMapper;
import org.example.qrcode_service_.repository.QrCodeRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class QrCodeService {
    private final Logger logger = Logger.getLogger(QrCodeService.class.getName());
    private final QRCodeWriter writer = new QRCodeWriter();
    private final QrCodeRepository qrCodeRepository;
    private final QrCodeMapper qrCodeMapper;


    public QrCodeEntity getEntityById(long id) {
        logger.info("Getting entity with id " + id);
        if (qrCodeRepository.existsById(id)){
            logger.info("Entity found with id " + id);
            return qrCodeRepository.getReferenceById(id);
        }
        logger.info("Entity does not exist with id  " + id);
        return null;
    }

    public byte[] rebuildQrcode(QrCodeEntity qrCodeEntity) {
        logger.info("Generating QR code for: " + qrCodeEntity.getContents());
        try {
            BitMatrix bitMatrix = writer.encode(qrCodeEntity.getContents(), BarcodeFormat.QR_CODE, qrCodeEntity.getSize(), qrCodeEntity.getSize());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, qrCodeEntity.getType().getName(), byteArrayOutputStream);
            logger.info("Generated QR code for: " + qrCodeEntity.getContents());
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return new byte[0];
        }
    }

    public byte[] generateQrCode(QrcodeRequestDto qrcodeRequestDto) {
        logger.info("Generating QR code for: " + qrcodeRequestDto.getContents());

        try {
            BitMatrix bitMatrix = writer.encode(qrcodeRequestDto.getContents(), BarcodeFormat.QR_CODE, qrcodeRequestDto.getSize(), qrcodeRequestDto.getSize());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, qrcodeRequestDto.getType().getName(), byteArrayOutputStream);

            QrCodeEntity qrCodeEntity = qrCodeMapper.toEntity(qrcodeRequestDto);
            qrCodeEntity.setCreatedAt(LocalDateTime.now());

            qrCodeRepository.save(qrCodeEntity);
            logger.info("Successfully generated QR code for: " + qrcodeRequestDto.getContents());
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return new byte[0];
        }

    }

    public List<QrcodeResponseDto> getAllQrCodes() {
        logger.severe("Getting all QR codes");
        return qrCodeRepository.findAll()
                .stream()
                .map(qrCodeMapper::toResponseDto)
                .toList();
    }

    public boolean deleteQrcode(long id) {
        logger.info("Deleting QR code with id: " + id);
        if (qrCodeRepository.existsById(id)) {
            qrCodeRepository.deleteById(id);
            logger.info("Deleted QR code with id: " + id);
            return true;
        }
        logger.warning("QR code with id: " + id + " not found");
        return false;
    }

    public MediaType getMediaTypeById(long id) {
        QrCodeEntity entity = getEntityById(id);

        return switch (entity.getType()) {
            case PNG -> MediaType.IMAGE_PNG;
            case GIF -> MediaType.IMAGE_GIF;
            default -> MediaType.IMAGE_JPEG;
        };
    }
}
