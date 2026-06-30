package com.kien.khachsan.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PHONG")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaPhong")
    private Long maPhong;

    @ManyToOne
    @JoinColumn(name = "MaKS")
    private Hotel hotel;

    @Column(name = "LoaiPhong", nullable = false, length = 50)
    private String loaiPhong;

    @Column(name = "GiaPhong", nullable = false, precision = 10, scale = 2)
    private BigDecimal giaPhong;

    @Column(name = "TinhTrang", length = 20)
    private String tinhTrang = "Trống";

    // ===== CONSTRUCTORS =====
    public Room() {}

    public Room(String loaiPhong, BigDecimal giaPhong, String tinhTrang) {
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
        this.tinhTrang = tinhTrang;
    }

    // ===== GETTERS & SETTERS =====
    public Long getMaPhong() { return maPhong; }
    public void setMaPhong(Long maPhong) { this.maPhong = maPhong; }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    public String getLoaiPhong() { return loaiPhong; }
    public void setLoaiPhong(String loaiPhong) { this.loaiPhong = loaiPhong; }

    public BigDecimal getGiaPhong() { return giaPhong; }
    public void setGiaPhong(BigDecimal giaPhong) { this.giaPhong = giaPhong; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }
}