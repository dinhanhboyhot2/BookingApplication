package com.example.demo.DTO.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BookingResponseDTO {
    
    private Long id;
    
    // Trích xuất thông tin phẳng để Frontend dễ bề hiển thị
    private Long guestId;
    private String guestName; // Lấy từ entity Guest
    
    private Long roomId;
    private String roomNumber; // Lấy từ entity Room
    
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    
    // Các thông tin hệ thống tự tính toán
    private BigDecimal totalPrice;
    private String status;
    
}