package com.kien.khachsan.dto.booking;

import java.time.LocalDate;

public class BookingRequest {
    private Long maKH;
    private Long maPhong;
    private LocalDate ngayNhanPhong;
    private LocalDate ngayTraPhong;

    // ===== CONSTRUCTORS =====
    public BookingRequest() {}

    public BookingRequest(Long maKH, Long maPhong, LocalDate ngayNhanPhong, LocalDate ngayTraPhong) {
        this.maKH = maKH;
        this.maPhong = maPhong;
        this.ngayNhanPhong = ngayNhanPhong;
        this.ngayTraPhong = ngayTraPhong;
    }

    // ===== GETTERS & SETTERS =====
    public Long getMaKH() { return maKH; }
    public void setMaKH(Long maKH) { this.maKH = maKH; }

    public Long getMaPhong() { return maPhong; }
    public void setMaPhong(Long maPhong) { this.maPhong = maPhong; }

    public LocalDate getNgayNhanPhong() { return ngayNhanPhong; }
    public void setNgayNhanPhong(LocalDate ngayNhanPhong) { this.ngayNhanPhong = ngayNhanPhong; }

    public LocalDate getNgayTraPhong() { return ngayTraPhong; }
    public void setNgayTraPhong(LocalDate ngayTraPhong) { this.ngayTraPhong = ngayTraPhong; }
}