package org.example.qrcode_service_.typeofqrcode;

public enum QrcodeType {
    GIF("GIF"),
    PNG("PNG"),
    JPEG("JPEG"),;

    private String name;

     QrcodeType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
