package com.example.demo.controlller;

import com.example.demo.DTO.request.LoginRequestDTO;
import com.example.demo.DTO.response.AuthResponseDTO;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // API Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    // API Đăng ký
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody LoginRequestDTO registerRequest) {
        AuthResponseDTO response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }
}