package com.example.demo.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    // Kéo cấu hình khóa bí mật từ application.properties
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    // Kéo cấu hình thời gian sống của token từ application.properties
    @Value("${app.jwt.expirationMs}")
    private int jwtExpirationMs;

    // Hàm phụ: Biến chuỗi text bí mật thành con dấu mã hóa thuật toán HMAC
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 1. HÀM IN THẺ (Sinh Token khi user đăng nhập thành công)
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .subject(username) // In tên người dùng lên thẻ
                .issuedAt(new Date()) // Thời điểm in thẻ
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Hạn sử dụng
                .signWith(getSigningKey()) // Đóng dấu đỏ bảo mật
                .compact(); // Nén lại thành chuỗi Text JWT
    }

    // 2. HÀM ĐỌC THẺ (Lấy tên người dùng từ Token gửi lên)
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Dùng đúng con dấu để quét và bóc niêm phong
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject(); // Lấy ra tên người dùng
    }

    // 3. HÀM KIỂM TRA THẺ (Xem thẻ là hàng thật hay giả, có bị hết hạn không)
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true; // Thẻ thật, còn hạn!
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Lỗi xác thực Token: " + e.getMessage());
        }
        return false; // Thẻ giả, thẻ rách, hoặc đã hết hạn!
    }
}