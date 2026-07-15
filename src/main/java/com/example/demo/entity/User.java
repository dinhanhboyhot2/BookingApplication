package com.example.demo.entity; // Đổi lại theo đúng package bạn đang dùng

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    // Lưu quyền hạn: ROLE_ADMIN, ROLE_USER...
    @Column(nullable = false)
    private String role; 
}