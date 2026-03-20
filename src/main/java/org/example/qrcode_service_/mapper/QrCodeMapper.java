package org.example.qrcode_service_.mapper;


import org.example.qrcode_service_.dto.QrcodeRequestDto;
import org.example.qrcode_service_.dto.QrcodeResponseDto;
import org.example.qrcode_service_.entity.QrCodeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QrCodeMapper {

    QrCodeEntity toEntity(QrcodeRequestDto qrcodeRequestDto);

    QrcodeResponseDto toResponseDto(QrCodeEntity qrCodeEntity);

}
