package com.example.demo.repository;

import com.example.demo.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Kỹ thuật JOIN FETCH: Lấy Data thật ngay từ đầu, tiêu diệt LazyInitializationException và N+1
    @Query("SELECT b FROM Booking b JOIN FETCH b.guest JOIN FETCH b.room")
    List<Booking> findAllOptimized();

}