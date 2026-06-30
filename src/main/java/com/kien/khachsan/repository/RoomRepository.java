package com.kien.khachsan.repository;

import com.kien.khachsan.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotel_MaKS(Long hotelId);
    List<Room> findByTinhTrang(String tinhTrang);
    List<Room> findByLoaiPhongContainingIgnoreCase(String loaiPhong);
}