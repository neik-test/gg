package com.kien.khachsan.dto.hotel;

public class HotelResponse {
    private Long maKS;
    private String tenKS;
    private String diaChi;
    private Integer soSao;
    private String moTa;

    // ===== CONSTRUCTORS =====
    public HotelResponse() {}

    public HotelResponse(Long maKS, String tenKS, String diaChi, Integer soSao, String moTa) {
        this.maKS = maKS;
        this.tenKS = tenKS;
        this.diaChi = diaChi;
        this.soSao = soSao;
        this.moTa = moTa;
    }

    // ===== GETTERS & SETTERS =====
    public Long getMaKS() { return maKS; }
    public void setMaKS(Long maKS) { this.maKS = maKS; }

    public String getTenKS() { return tenKS; }
    public void setTenKS(String tenKS) { this.tenKS = tenKS; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public Integer getSoSao() { return soSao; }
    public void setSoSao(Integer soSao) { this.soSao = soSao; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
}