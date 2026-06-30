package com.kien.khachsan.dto.customer;

public class CustomerResponse {
    private Long maKH;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String cccd;
    private String diaChi;

    // ===== CONSTRUCTORS =====
    public CustomerResponse() {}

    public CustomerResponse(Long maKH, String hoTen, String soDienThoai, String email, String cccd, String diaChi) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.cccd = cccd;
        this.diaChi = diaChi;
    }

    // ===== GETTERS & SETTERS =====
    public Long getMaKH() { return maKH; }
    public void setMaKH(Long maKH) { this.maKH = maKH; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
}