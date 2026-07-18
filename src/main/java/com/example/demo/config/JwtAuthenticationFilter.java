package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    // Gọi máy quét thẻ JwtUtils vào trạm gác
    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 1. Lấy thẻ JWT từ trong Request Header do Postman/Mobile gửi lên
            String jwt = getJwtFromRequest(request);

            // 2. Bật máy quét kiểm tra thẻ
            if (StringUtils.hasText(jwt) && jwtUtils.validateJwtToken(jwt)) {

                // 3. Thẻ thật -> Lấy tên người dùng ra
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // 4. Tạo "Giấy thông hành" nội bộ cho Spring Security
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList());

                // 5. Đóng dấu duyệt, cho phép người này đi qua và lưu vào hệ thống
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.err.println("Không thể xác thực người dùng: " + e.getMessage());
        }

        // 6. Mở barie cho Request đi tiếp vào Tầng Controller
        filterChain.doFilter(request, response);
    }

    // Hàm phụ: Bóc tách chuỗi Token từ chữ "Bearer " ở Header
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Theo chuẩn quốc tế, Token phải nằm ở Header Authorization và bắt đầu bằng chữ "Bearer "
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Cắt bỏ 7 ký tự "Bearer " để lấy lõi Token
        }
        return null;
    }
}