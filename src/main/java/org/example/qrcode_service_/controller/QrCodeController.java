package org.example.qrcode_service_.controller;

import jakarta.validation.Valid;
import org.example.qrcode_service_.baseurl.BaseUrl;
import org.example.qrcode_service_.dto.QrcodeRequestDto;
import org.example.qrcode_service_.dto.QrcodeResponseDto;
import org.example.qrcode_service_.entity.QrCodeEntity;
import org.example.qrcode_service_.service.QrCodeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(BaseUrl.url)
public class QrCodeController {
    private final QrCodeService qrCodeService;

    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping()
    public ResponseEntity<byte[]> createQrcode(@Valid @RequestBody QrcodeRequestDto requestDto) {
        MediaType mediaType = switch (requestDto.getType()) {
            case PNG -> MediaType.IMAGE_PNG;
            case GIF -> MediaType.IMAGE_GIF;
            default -> MediaType.IMAGE_JPEG;
        };

        byte[] qrcode = qrCodeService.generateQrCode(requestDto);

        return ResponseEntity.ok().contentType(mediaType).body(qrcode);

    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getQrcode(@PathVariable long id) {
        QrCodeEntity qrCodeEntity = qrCodeService.getEntityById(id);
        MediaType mediaType = qrCodeService.getMediaTypeById(id);



        byte[] qrcodeById = qrCodeService.rebuildQrcode(qrCodeEntity);

        return ResponseEntity.ok().contentType(mediaType).body(qrcodeById);
    }

    @GetMapping("all")
    public ResponseEntity<List<QrcodeResponseDto>> getAllQrCodes() {
        return ResponseEntity
                .ok()
                .body(qrCodeService.getAllQrCodes());
    }

    @DeleteMapping("{id}")
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
