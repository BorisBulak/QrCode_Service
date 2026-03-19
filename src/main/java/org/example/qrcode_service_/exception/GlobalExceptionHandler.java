package org.example.qrcode_service_.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e){
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

}
