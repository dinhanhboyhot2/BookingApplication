package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
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
    // 2. HÀM MỚI: Bắt toàn bộ lỗi Validation từ các class DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value()); // Mã 400
        body.put("error", "Dữ liệu đầu vào không hợp lệ");

        // Tạo một Map con để chứa danh sách chi tiết các trường bị lỗi
        Map<String, String> errors = new HashMap<>();

        // Vòng lặp: Lấy từng lỗi trong DTO ra và nhét vào Map
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        body.put("details", errors);

        // Trả về JSON cho Mobile/Frontend
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}