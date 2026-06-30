package com.kien.khachsan.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DAT_PHONG")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaDatPhong")
    private Long maDatPhong;

    @ManyToOne
    @JoinColumn(name = "MaKH", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "MaPhong", nullable = false)
    private Room room;

    @Column(name = "ngaydat", nullable = false)
    private LocalDate ngayDat = LocalDate.now();

    @Column(name = "NgayNhanPhong", nullable = false)
    private LocalDate ngayNhanPhong;

    @Column(name = "NgayTraPhong", nullable = false)
    private LocalDate ngayTraPhong;

    @Column(name = "TongTien", precision = 10, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "TrangThai", nullable = false)
    private String trangThai = "Chua thanh toan";

    // ===== CONSTRUCTORS =====
    public Booking() {}

    public Booking(Customer customer, Room room, LocalDate ngayNhanPhong, LocalDate ngayTraPhong) {
        this.customer = customer;
        this.room = room;
        this.ngayNhanPhong = ngayNhanPhong;
        this.ngayTraPhong = ngayTraPhong;
    }

    // ===== GETTERS & SETTERS =====
    public Long getMaDatPhong() { return maDatPhong; }
    public void setMaDatPhong(Long maDatPhong) { this.maDatPhong = maDatPhong; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

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