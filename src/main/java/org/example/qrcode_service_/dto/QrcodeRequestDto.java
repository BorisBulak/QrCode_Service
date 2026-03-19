package org.example.qrcode_service_.dto;

import jakarta.validation.constraints.*;
import org.example.qrcode_service_.correctiontypes.Correction;
import org.example.qrcode_service_.typeofqrcode.QrcodeType;




public class QrcodeRequestDto {
    @NotBlank(message = "Contents must be filled")
    private String contents;

    @NotNull(message = "Correction Must be : L,M,Q,H")
    private Correction correction = Correction.L;

    @Min(value = 150,message = "Size must be at least 150")
    @Max(value = 350,message = "Size must be at most 350")
    private int size=250;

    @NotNull(message = "Type must be: GIF, JPEG, PNG")
    private QrcodeType type=QrcodeType.PNG;

    public String getContents() {
        return contents;
    }

    public Correction getCorrection() {
        return correction;
    }

    public QrcodeType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCorrection(Correction correction) {
        this.correction = correction;
    }

    public void setType(QrcodeType type) {
        this.type = type;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
