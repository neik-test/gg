package com.kien.khachsan.dto.booking;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingResponse {
    private Long maDatPhong;
    private Long maKH;
    private Long maPhong;
    private LocalDate ngayDat;
    private LocalDate ngayNhanPhong;
    private LocalDate ngayTraPhong;
    private BigDecimal tongTien;
    private String trangThai;

    // ===== CONSTRUCTORS =====
    public BookingResponse() {}

    public BookingResponse(Long maDatPhong, Long maKH, Long maPhong, LocalDate ngayDat,
                           LocalDate ngayNhanPhong, LocalDate ngayTraPhong,
                           BigDecimal tongTien, String trangThai) {
        this.maDatPhong = maDatPhong;
        this.maKH = maKH;
        this.maPhong = maPhong;
        this.ngayDat = ngayDat;
        this.ngayNhanPhong = ngayNhanPhong;
        this.ngayTraPhong = ngayTraPhong;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // ===== GETTERS & SETTERS =====
    public Long getMaDatPhong() { return maDatPhong; }
    public void setMaDatPhong(Long maDatPhong) { this.maDatPhong = maDatPhong; }

    public Long getMaKH() { return maKH; }
    public void setMaKH(Long maKH) { this.maKH = maKH; }

    public Long getMaPhong() { return maPhong; }
    public void setMaPhong(Long maPhong) { this.maPhong = maPhong; }

    public LocalDate getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDate ngayDat) { this.ngayDat = ngayDat; }

    public LocalDate getNgayNhanPhong() { return ngayNhanPhong; }
    public void setNgayNhanPhong(LocalDate ngayNhanPhong) { this.ngayNhanPhong = ngayNhanPhong; }

    public LocalDate getNgayTraPhong() { return ngayTraPhong; }
    public void setNgayTraPhong(LocalDate ngayTraPhong) { this.ngayTraPhong = ngayTraPhong; }

    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}