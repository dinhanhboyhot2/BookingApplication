package com.example.demo.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token; // Chứa chuỗi thẻ từ JWT
    private String username;
    private String role;
}