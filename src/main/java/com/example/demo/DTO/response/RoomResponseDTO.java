package com.example.demo.DTO.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class RoomResponseDTO {
    // Chứa những thông tin an toàn để hiển thị
    private Long id;
    private String roomNumber;
    private String type;
    private BigDecimal price;
    private String status;
}