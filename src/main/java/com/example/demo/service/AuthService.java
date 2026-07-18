package com.example.demo.service;

import com.example.demo.DTO.request.LoginRequestDTO;
import com.example.demo.DTO.response.AuthResponseDTO;
import com.example.demo.config.JwtUtils;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    public AuthResponseDTO login(LoginRequestDTO request) {
        // 1. Tìm user trong Database
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy tài khoản!"));
        boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            throw new RuntimeException("Lỗi: Mật khẩu không chính xác!");
        }
        // 3. Đăng nhập thành công -> In thẻ JWT
        String jwtToken = jwtUtils.generateJwtToken(user.getUsername());
        return new AuthResponseDTO(jwtToken, user.getUsername(), user.getRole());
    }
    public AuthResponseDTO register(LoginRequestDTO request) {
        // Kiểm tra xem tên đăng nhập đã bị trùng chưa
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Lỗi: Tên đăng nhập đã tồn tại!");
        }

        // Tạo tài khoản mới
        User user = new User();
        user.setUsername(request.getUsername());
        // QUAN TRỌNG: Phải băm mật khẩu trước khi lưu xuống DB
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER"); // Mặc định là khách hàng

        userRepository.save(user);

        // Đăng ký xong thì tự động đăng nhập luôn để in thẻ
        return login(request);
    }
}
