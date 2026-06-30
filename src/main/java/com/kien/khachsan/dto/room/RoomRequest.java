package com.kien.khachsan.dto.room;

import java.math.BigDecimal;

public class RoomRequest {
    private Long maKS;
    private String loaiPhong;
    private BigDecimal giaPhong;
    private String tinhTrang;

    // ===== CONSTRUCTORS =====
    public RoomRequest() {}

    public RoomRequest(Long maKS, String loaiPhong, BigDecimal giaPhong, String tinhTrang) {
        this.maKS = maKS;
        this.loaiPhong = loaiPhong;
        this.giaPhong = giaPhong;
        this.tinhTrang = tinhTrang;
    }

    // ===== GETTERS & SETTERS =====
    public Long getMaKS() { return maKS; }
    public void setMaKS(Long maKS) { this.maKS = maKS; }

    public String getLoaiPhong() { return loaiPhong; }
    public void setLoaiPhong(String loaiPhong) { this.loaiPhong = loaiPhong; }

    public BigDecimal getGiaPhong() { return giaPhong; }
    public void setGiaPhong(BigDecimal giaPhong) { this.giaPhong = giaPhong; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }
}