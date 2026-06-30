package com.kien.khachsan.dto.room;

import java.math.BigDecimal;

public class RoomResponse {
    private Long maPhong;
    private Long maKS;
    private String loaiPhong;
    private BigDecimal giaPhong;
    private String tinhTrang;

    // ===== CONSTRUCTORS =====
    public RoomResponse() {}

    public RoomResponse(Long maPhong, Long maKS, String loaiPhong, BigDecimal giaPhong, String tinhTrang) {
        this.maPhong = maPhong;
        this.maKS = maKS;
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
        this.tinhTrang = tinhTrang;
    }

    // ===== GETTERS & SETTERS =====
    public Long getMaPhong() { return maPhong; }
    public void setMaPhong(Long maPhong) { this.maPhong = maPhong; }

    public Long getMaKS() { return maKS; }
    public void setMaKS(Long maKS) { this.maKS = maKS; }

    public String getLoaiPhong() { return loaiPhong; }
    public void setLoaiPhong(String loaiPhong) { this.loaiPhong = loaiPhong; }

    public BigDecimal getGiaPhong() { return giaPhong; }
    public void setGiaPhong(BigDecimal giaPhong) { this.giaPhong = giaPhong; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }
}