package com.example.demo.exception;

// Kế thừa RuntimeException để không phải bắt try-catch rườm rà ở mọi nơi
public class RoomNotAvailableException extends RuntimeException {
    
    public RoomNotAvailableException(String message) {
        super(message);
    }
}