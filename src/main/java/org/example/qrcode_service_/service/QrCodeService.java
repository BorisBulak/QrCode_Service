package org.example.qrcode_service_.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.example.qrcode_service_.correctiontypes.Correction;
import org.example.qrcode_service_.entity.Entity;
import org.example.qrcode_service_.repository.QrCodeRepository;
import org.example.qrcode_service_.typeofqrcode.QrcodeType;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class QrCodeService {
    private final QRCodeWriter writer = new QRCodeWriter();
    private final QrCodeRepository qrCodeRepository;

    public QrCodeService(QrCodeRepository qrCodeRepository) {
        this.qrCodeRepository = qrCodeRepository;
    }

    public byte[] generateQrCode(String contents, Integer size, Correction correction, QrcodeType type){
        if (contents == null || contents.isBlank()){
            throw new IllegalArgumentException("It cannot be blank");
        }

        if (size < 150 || size > 350){
            throw new IllegalArgumentException("Size must be between 150 and 350");
        }

        if (!"L".equals(correction.getCorrection()) && !"M".equals(correction.getCorrection()) && !"Q".equals(correction.getCorrection()) && !"H".equals(correction.getCorrection())){
            throw new IllegalArgumentException("Permitted error correction levels are L, M, Q, H");
        }

        if (!"jpeg".equals(type.getName()) && !"gif".equals(type.getName()) && !"png".equals(type.getName())){
            throw new IllegalArgumentException("Only png, jpeg and gif image types are supported");
        }

        try {
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE,size,size);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,type.getName(),byteArrayOutputStream);

            Entity entity = new Entity();
            entity.setContents(contents);
            entity.setCorrection(correction);
            entity.setType(type);
            entity.setSize(size);
            entity.setCreatedAt(LocalDateTime.now());

            qrCodeRepository.save(entity);


            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            System.out.println("Exception occurred while generating QR code");
            return new byte[0];
        }

    }

    public List<Entity> getAllQrCodes(){
        return qrCodeRepository.findAll();
    }

    public boolean deleteQrcode(long id){
        if (qrCodeRepository.existsById(id)){
            qrCodeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
