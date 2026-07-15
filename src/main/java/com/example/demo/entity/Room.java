package com.example.demo.entity;

import com.example.demo.enums.RoomStatus;
import com.example.demo.enums.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter @Setter @NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false, length = 10, unique = true)
    private String roomNumber;

    @Enumerated(EnumType.STRING) // Lưu dưới DB là Text ('STANDARD', 'DELUXE'...)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "price_per_night", nullable = false)
    private BigDecimal pricePerNight; // Map với DECIMAL(12,2)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status = RoomStatus.AVAILABLE;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}