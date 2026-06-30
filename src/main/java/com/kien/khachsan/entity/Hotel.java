package com.kien.khachsan.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "KHACH_SAN")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKS")
    private Long maKS;

    @Column(name = "TenKS", nullable = false, length = 100)
    private String tenKS;

    @Column(name = "DiaChi", nullable = false, length = 255)
    private String diaChi;

    @Column(name = "SoSao")
    private Integer soSao;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;

    // Quan hệ với Room (1-N)
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms = new ArrayList<>();

    // ===== CONSTRUCTORS =====
    public Hotel() {
    }

    public Hotel(String tenKS, String diaChi, Integer soSao, String moTa) {
        this.tenKS = tenKS;
        this.diaChi = diaChi;
        this.soSao = soSao;
        this.moTa = moTa;
    }

    // ===== GETTERS & SETTERS =====
    public Long getMaKS() {
        return maKS;
    }

    public void setMaKS(Long maKS) {
        this.maKS = maKS;
    }

    public String getTenKS() {
        return tenKS;
    }

    public void setTenKS(String tenKS) {
        this.tenKS = tenKS;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Integer getSoSao() {
        return soSao;
    }

    public void setSoSao(Integer soSao) {
        this.soSao = soSao;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}