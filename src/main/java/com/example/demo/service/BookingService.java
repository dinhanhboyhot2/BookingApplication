package com.example.demo.service;

import com.example.demo.DTO.request.BookingRequestDTO;
import com.example.demo.DTO.response.BookingResponseDTO;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Guest;
import com.example.demo.entity.Room;
import com.example.demo.enums.BookingStatus;
import com.example.demo.enums.RoomStatus;
import com.example.demo.exception.RoomNotAvailableException;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.GuestRepository;
import com.example.demo.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {

    // Service này cần tận 3 công cụ để làm việc
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository, GuestRepository guestRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
    }

    // Annotation cực kỳ quan trọng giúp bảo vệ toàn vẹn dữ liệu
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO request) {

        // 1. Tìm Khách hàng và Phòng từ Database
        Guest guest = guestRepository.findById(request.getGuestId())
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy ID Khách hàng!"));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Lỗi: Không tìm thấy ID Phòng!"));

        // 2. Kiểm tra nghiệp vụ: Phòng có trống không?
        if (room.getStatus() != RoomStatus.AVAILABLE) {
            // Tái sử dụng Custom Exception bạn đã viết ở Tuần 1
            throw new RoomNotAvailableException("Phòng " + room.getRoomNumber() + " hiện không khả dụng để đặt!");
        }

        // 3. Tính toán số ngày ở và tổng tiền
        long daysBetween = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        if (daysBetween <= 0) {
            throw new RuntimeException("Lỗi: Ngày trả phòng phải sau ngày nhận phòng!");
        }

        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(daysBetween));

        // 4. Bắt đầu đóng gói Entity để lưu xuống Database
        Booking booking = new Booking();
        booking.setGuest(guest);
        booking.setRoom(room);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(BookingStatus.PENDING); // Đặt mặc định là PENDING (Chờ duyệt)

        // Lưu vào MySQL
        Booking savedBooking = bookingRepository.save(booking);

        // 5. Phiên dịch sang DTO để trả về cho Controller
        return convertToResponseDTO(savedBooking);
    }

    // Hàm phụ: Chuyển từ Entity sang DTO
    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getId());

        // Trích xuất phẳng dữ liệu
        dto.setGuestId(booking.getGuest().getId());
        dto.setGuestName(booking.getGuest().getFullName()); // Lấy tên từ bảng guests

        dto.setRoomId(booking.getRoom().getId());
        dto.setRoomNumber(booking.getRoom().getRoomNumber()); // Lấy số phòng từ bảng rooms

        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());
        dto.setTotalPrice(booking.getTotalPrice());
        dto.setStatus(booking.getStatus().name());

        return dto;
    }
}