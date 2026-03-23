package org.example.qrcode_service_.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.example.qrcode_service_.enums.Correction;
import org.example.qrcode_service_.enums.QrcodeType;


@Getter
@Setter

public class QrcodeRequestDto {
    @NotBlank(message = "Contents must be filled")
    private String contents;

    @NotNull(message = "Correction Must be : L,M,Q,H")
    private Correction correction = Correction.L;

    @Min(value = 150, message = "Size must be at least 150")
    @Max(value = 350, message = "Size must be at most 350")
    private int size = 250;

    @NotNull(message = "Type must be: GIF, JPEG, PNG")
    private QrcodeType type = QrcodeType.PNG;
}
