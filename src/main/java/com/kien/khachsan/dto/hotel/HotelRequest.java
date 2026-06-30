package com.kien.khachsan.dto.hotel;

public class HotelRequest {
    private String tenKS;
    private String diaChi;
    private Integer soSao;
    private String moTa;

    // ===== CONSTRUCTORS =====
    public HotelRequest() {}

    public HotelRequest(String tenKS, String diaChi, Integer soSao, String moTa) {
        this.tenKS = tenKS;
        this.diaChi = diaChi;
        this.soSao = soSao;
        this.moTa = moTa;
    }

    // ===== GETTERS & SETTERS =====
    public String getTenKS() { return tenKS; }
    public void setTenKS(String tenKS) { this.tenKS = tenKS; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public Integer getSoSao() { return soSao; }
    public void setSoSao(Integer soSao) { this.soSao = soSao; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
}