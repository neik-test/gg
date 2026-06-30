package com.kien.khachsan.service;

import com.kien.khachsan.entity.Room;
import com.kien.khachsan.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Lấy tất cả phòng
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Lấy phòng theo ID
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại với ID: " + id));
    }

    // Lấy phòng theo khách sạn
    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotel_MaKS(hotelId);
    }

    // Lấy phòng theo trạng thái
    public List<Room> getRoomsByStatus(String status) {
        return roomRepository.findByTinhTrang(status);
    }

    // Lấy phòng theo loại phòng
    public List<Room> getRoomsByType(String type) {
        return roomRepository.findByLoaiPhongContainingIgnoreCase(type);
    }

    // Tạo phòng mới
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    // Cập nhật phòng
    public Room updateRoom(Long id, Room roomDetails) {
        Room existingRoom = getRoomById(id);

        if (roomDetails.getLoaiPhong() != null) {
            existingRoom.setLoaiPhong(roomDetails.getLoaiPhong());
        }
        if (roomDetails.getGiaPhong() != null) {
            existingRoom.setGiaPhong(roomDetails.getGiaPhong());
        }
        if (roomDetails.getTinhTrang() != null) {
            existingRoom.setTinhTrang(roomDetails.getTinhTrang());
        }
        if (roomDetails.getHotel() != null) {
            existingRoom.setHotel(roomDetails.getHotel());
        }

        return roomRepository.save(existingRoom);
    }

    // Cập nhật trạng thái phòng
    public Room updateRoomStatus(Long id, String status) {
        Room room = getRoomById(id);
        room.setTinhTrang(status);
        return roomRepository.save(room);
    }

    // Xóa phòng
    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }
}