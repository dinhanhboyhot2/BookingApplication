package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "guests")
@Getter @Setter @NoArgsConstructor
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khớp với AUTO_INCREMENT trong MySQL
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 20, unique = true)
    private String phone;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Quan hệ 1-N: 1 Khách có thể có nhiều Lần đặt phòng
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}