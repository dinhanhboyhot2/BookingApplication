package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Dặn hệ thống: Nếu thấy lỗi RoomNotAvailableException thì chạy vào hàm này
    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<Object> handleRoomNotAvailable(RoomNotAvailableException ex) {

        // Tạo một cục JSON (Map) để định dạng lại câu trả lời
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value()); // Mã 400
        body.put("error", "Phòng không khả dụng");
        body.put("message", ex.getMessage()); // Lấy câu thông báo lỗi truyền vào

        // Trả về cho Frontend/Mobile với mã lỗi 400 (Bad Request)
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}