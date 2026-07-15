package com.example.demo.controlller;

import com.example.demo.DTO.response.RoomResponseDTO;
import com.example.demo.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    // Tiêm RoomService vào Controller thông qua Constructor
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // Endpoint: Lấy danh sách toàn bộ phòng (Trả về danh sách DTO)
    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        List<RoomResponseDTO> rooms = roomService.getAllRooms();
        // Trả về HTTP Status 200 (OK) cùng với dữ liệu
        return ResponseEntity.ok(rooms);
    }
}