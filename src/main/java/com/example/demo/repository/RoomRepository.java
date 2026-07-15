package com.example.demo.repository;

import com.example.demo.entity.Room;
import com.example.demo.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Chỉ cần để trống thế này, Spring Boot sẽ tự động sinh ra sẵn 
    // các hàm như findAll(), findById(), save(), delete()... cho bạn!

}