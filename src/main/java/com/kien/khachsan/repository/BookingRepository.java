package com.kien.khachsan.repository;

import com.kien.khachsan.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer_MaKH(Long maKH);
    List<Booking> findByRoom_MaPhong(Long maPhong);
    List<Booking> findByTrangThai(String trangThai);
    
    @Query("SELECT b FROM Booking b WHERE b.room.maPhong = :roomId " +
           "AND b.ngayNhanPhong < :checkOut AND b.ngayTraPhong > :checkIn " +
           "AND b.trangThai != 'Hủy'")
    List<Booking> findConflictingBookings(@Param("roomId") Long roomId,
                                          @Param("checkIn") LocalDate checkIn,
                                          @Param("checkOut") LocalDate checkOut);
}