package org.example.qrcode_service_.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.example.qrcode_service_.correctiontypes.Correction;
import org.example.qrcode_service_.dto.QrcodeRequestDto;
import org.example.qrcode_service_.dto.QrcodeResponseDto;
import org.example.qrcode_service_.entity.QrCodeEntity;
import org.example.qrcode_service_.mapper.QrCodeMapper;
import org.example.qrcode_service_.repository.QrCodeRepository;
import org.example.qrcode_service_.typeofqrcode.QrcodeType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;


@Service
public class QrCodeService {
    private final Logger logger = Logger.getLogger(QrCodeService.class.getName());
    private final QRCodeWriter writer = new QRCodeWriter();
    private final QrCodeRepository qrCodeRepository;
    private final QrCodeMapper qrCodeMapper;

    public QrCodeService(QrCodeRepository qrCodeRepository, QrCodeMapper qrCodeMapper) {
        this.qrCodeRepository = qrCodeRepository;
        this.qrCodeMapper = qrCodeMapper;
    }

    public QrCodeEntity toEntity(QrcodeRequestDto qrcodeRequestDto) {
        return qrCodeMapper.toEntity(qrcodeRequestDto);
    }

    public QrcodeResponseDto toResponseDto(QrCodeEntity qrCodeEntity) {
        return qrCodeMapper.toResponseDto(qrCodeEntity);
    }


    public QrCodeEntity getEntityById(long id) {
        logger.info("Getting entity with id " + id);
        return qrCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QR code not found with id: " + id));
    }


    public byte[] getQrcodeById(long id) {
        logger.info("Getting QR code with id: " + id);
        QrCodeEntity qrCodeEntity = qrCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QrCode with id " + id + " not found"));

        return rebuildQrcode(
                qrCodeEntity.getContents(),
                qrCodeEntity.getSize(),
                qrCodeEntity.getCorrection(),
                qrCodeEntity.getType()
        );
    }


    public byte[] rebuildQrcode(String contents, Integer size, Correction correction, QrcodeType type) {
        logger.info("Generating QR code for: " + contents);
        try {
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, type.getName(), byteArrayOutputStream);
            logger.info("Generated QR code for: " + contents);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            logger.severe(e.getMessage());
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
            logger.severe(e.getMessage());
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
}
