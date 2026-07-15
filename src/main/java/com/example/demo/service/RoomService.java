package com.example.demo.service;

import com.example.demo.DTO.response.RoomResponseDTO;
import com.example.demo.DTO.request.RoomRequestDTO;
import com.example.demo.entity.Room;
import com.example.demo.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Hàm lấy danh sách phòng: Trả về DTO thay vì Entity
    public List<RoomResponseDTO> getAllRooms() {
        // 1. Lấy danh sách Entity từ Database
        List<Room> rooms = roomRepository.findAll();

        // 2. Chuyển đổi List<Room> thành List<RoomResponseDTO> bằng Stream API
        return rooms.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Hàm phiên dịch thủ công từ Entity sang DTO
    // Hàm phiên dịch thủ công từ Entity sang DTO
    private RoomResponseDTO convertToResponseDTO(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());

        // SỬA LỖI Ở ĐÂY: Dùng getRoomType() và chuyển Enum sang String
        if (room.getRoomType() != null) {
            dto.setType(room.getRoomType().name());
        }

        // SỬA LỖI Ở ĐÂY: Dùng getPricePerNight() thay vì getPrice()
        dto.setPrice(room.getPricePerNight());

        // Giả sử Enum Status có hàm name() để chuyển thành String
        if (room.getStatus() != null) {
            dto.setStatus(room.getStatus().name());
        }
        return dto;
    }
}