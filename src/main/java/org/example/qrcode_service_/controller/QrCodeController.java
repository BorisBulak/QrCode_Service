package org.example.qrcode_service_.controller;

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
    public ResponseEntity<byte[]> qrcode(@RequestParam String contents, @RequestParam(defaultValue = "250") int size, @RequestParam(defaultValue = "L") String correction, @RequestParam(defaultValue = "png") String type) {
        MediaType mediaType = switch (type) {
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            default -> MediaType.IMAGE_JPEG;
        };

        byte[] qrcode = qrCodeService.generateQrCode(contents, size, correction, type);
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
