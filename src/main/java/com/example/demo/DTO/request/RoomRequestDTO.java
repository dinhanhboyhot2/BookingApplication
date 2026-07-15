package com.example.demo.DTO.request;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class RoomRequestDTO {
    // Chỉ chứa những thông tin người dùng được phép nhập
    private String roomNumber;
    private String type;
    private BigDecimal price;
}