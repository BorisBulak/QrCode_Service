package org.example.qrcode_service_.controller;

import org.example.qrcode_service_.dto.QrcodeRequestDto;
import org.example.qrcode_service_.entity.Entity;
import org.example.qrcode_service_.service.QrCodeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
public class QrCodeController {
    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> qrcode(@RequestBody QrcodeRequestDto requestDto) {
        MediaType mediaType = switch (requestDto.getType()) {
            case PNG -> MediaType.IMAGE_PNG;
            case GIF -> MediaType.IMAGE_GIF;
            default -> MediaType.IMAGE_JPEG;
        };

        byte[] qrcode = qrCodeService.generateQrCode(requestDto.getContents(), requestDto.getSize(), requestDto.getCorrection(), requestDto.getType());
        return ResponseEntity.ok().contentType(mediaType).body(qrcode);

    }

    @GetMapping("/allQrcodes")
    public ResponseEntity<List<Entity>> getAllQrCodes() {
        return ResponseEntity
                .ok()
                .body(qrCodeService.getAllQrCodes());
    }

    @DeleteMapping("/deleteQrCode/{id}")
    public ResponseEntity<String> deleteQrCode(@PathVariable long id) {
        boolean deleted = qrCodeService.deleteQrcode(id);
        if (deleted) {
            return ResponseEntity
                    .ok("Delete QrCode successfully");
        } else {
            return ResponseEntity
                    .status(404)
                    .body("Delete QrCode failed");
        }
    }
}
