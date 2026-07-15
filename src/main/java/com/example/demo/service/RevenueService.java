package com.example.demo.service;

import com.example.demo.entity.Booking;
import com.example.demo.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RevenueService {

    private final BookingRepository bookingRepository;

    // Tạo ra 1 luồng thợ xây (Worker Thread) chạy ngầm
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Biến lưu trữ tổng doanh thu an toàn cho đa luồng
    private final AtomicReference<BigDecimal> totalRevenueCache = new AtomicReference<>(BigDecimal.ZERO);

    public RevenueService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Hàm này sẽ giao việc cho luồng phụ rồi kết thúc ngay lập tức
    public void calculateRevenueInBackground() {
        System.out.println("[Luồng Chính] Đã ra lệnh tính doanh thu. Tôi đi làm việc khác đây...");

        executor.submit(() -> {
            try {
                System.out.println("   -> [Luồng Phụ] Đang cặm cụi tính toán doanh thu (Giả lập mất 3 giây)...");
                Thread.sleep(3000); // Giả lập việc tính toán tốn thời gian

                List<Booking> bookings = bookingRepository.findAllOptimized();
                BigDecimal sum = bookings.stream()
                        .map(Booking::getTotalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                totalRevenueCache.set(sum); // Cập nhật kết quả an toàn
                System.out.println("   -> [Luồng Phụ] TÍNH TOÁN XONG! Tổng doanh thu là: " + sum + " VNĐ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    // Hàm để lấy kết quả hiện tại
    public BigDecimal getCurrentRevenue() {
        return totalRevenueCache.get();
    }
}