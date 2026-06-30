package com.kien.khachsan.controller;

import com.kien.khachsan.dto.room.RoomRequest;
import com.kien.khachsan.dto.room.RoomResponse;
import com.kien.khachsan.entity.Hotel;
import com.kien.khachsan.entity.Room;
import com.kien.khachsan.service.HotelService;
import com.kien.khachsan.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;

    public RoomController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    // ===== PUBLIC APIs =====
    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomResponse> responses = rooms.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(convertToResponse(room));
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<RoomResponse>> getRoomsByHotel(@PathVariable Long hotelId) {
        List<Room> rooms = roomService.getRoomsByHotel(hotelId);
        List<RoomResponse> responses = rooms.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RoomResponse>> getRoomsByStatus(@PathVariable String status) {
        List<Room> rooms = roomService.getRoomsByStatus(status);
        List<RoomResponse> responses = rooms.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoomResponse>> searchRoomsByType(@RequestParam String type) {
        List<Room> rooms = roomService.getRoomsByType(type);
        List<RoomResponse> responses = rooms.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // ===== ADMIN APIs =====
    @PostMapping
    @PreAuthorize("hasAuthority('room:write')")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest request) {
        Hotel hotel = hotelService.getHotelById(request.getMaKS());
        
        Room room = new Room();
        room.setLoaiPhong(request.getLoaiPhong());
        room.setGiaPhong(request.getGiaPhong());
        room.setTinhTrang(request.getTinhTrang() != null ? request.getTinhTrang() : "Trống");
        room.setHotel(hotel);
        
        Room savedRoom = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(savedRoom));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('room:write')")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @RequestBody RoomRequest request) {
        Room room = convertToEntity(request);
        Room updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok(convertToResponse(updatedRoom));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('room:delete')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('room:write')")
    public ResponseEntity<RoomResponse> updateRoomStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Room updatedRoom = roomService.updateRoomStatus(id, status);
        return ResponseEntity.ok(convertToResponse(updatedRoom));
    }

    // ===== CONVERTERS =====
    private RoomResponse convertToResponse(Room room) {
        return new RoomResponse(
                room.getMaPhong(),
                room.getHotel() != null ? room.getHotel().getMaKS() : null,
                room.getLoaiPhong(),
                room.getGiaPhong(),
                room.getTinhTrang()
        );
    }

    private Room convertToEntity(RoomRequest request) {
        Room room = new Room();
        room.setLoaiPhong(request.getLoaiPhong());
        room.setGiaPhong(request.getGiaPhong());
        room.setTinhTrang(request.getTinhTrang());
        if (request.getMaKS() != null) {
            Hotel hotel = hotelService.getHotelById(request.getMaKS());
            room.setHotel(hotel);
        }
        return room;
    }
}