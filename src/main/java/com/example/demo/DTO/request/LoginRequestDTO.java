package com.example.demo.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    @NotBlank(message = "Vui lòng nhập tên tài khoản")
    private String username;

    @NotBlank(message = "Vui lòng nhập mật khẩu")
    private String password;
}