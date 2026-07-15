package com.example.demo.controlller;

import com.example.demo.DTO.request.BookingRequestDTO;
import com.example.demo.DTO.response.BookingResponseDTO;
import com.example.demo.exception.RoomNotAvailableException;
import com.example.demo.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    // Tiêm BookingService vào thông qua Constructor
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // --------------------------------------------------------
    // ENDPOINT MỚI: API Tạo đơn đặt phòng
    // --------------------------------------------------------
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO request) {
        // Gọi xuống tầng Service để xử lý logic
        BookingResponseDTO response = bookingService.createBooking(request);

        // Trả về kết quả kèm mã HTTP 201 (Created - Đã tạo thành công)
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // --------------------------------------------------------
    // API Cũ từ Tuần 1 (Giữ nguyên để test GlobalExceptionHandler)
    // --------------------------------------------------------
    @GetMapping("/test-error")
    public String testError() {
        throw new RoomNotAvailableException("Phòng 101 hiện đang có khách ở, vui lòng chọn phòng khác!");
    }
}