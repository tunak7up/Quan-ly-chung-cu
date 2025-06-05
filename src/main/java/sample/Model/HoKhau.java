package main.java.sample.Model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class HoKhau {
    private int soHoKhau;
    private String soNha;
    private String duong;
    private String phuong;
    private String quan;
    private LocalDate ngayLamHoKhau;

    public HoKhau(int soHoKhau, String soNha, String duong, String phuong, String quan, LocalDate ngayLamHoKhau) {
        this.soHoKhau = soHoKhau;
        this.soNha = soNha;
        this.duong = duong;
        this.phuong = phuong;
        this.quan = quan;
        this.ngayLamHoKhau = ngayLamHoKhau;
    }

    public int getSoHoKhau() {
        return soHoKhau;
    }

    public void setSoHoKhau(int soHoKhau) {
        this.soHoKhau = soHoKhau;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }

    public String getDuong() {
        return duong;
    }

    public void setDuong(String duong) {
        this.duong = duong;
    }

    public String getPhuong() {
        return phuong;
    }

    public void setPhuong(String phuong) {
        this.phuong = phuong;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public LocalDate getNgayLamHoKhau() {
        return ngayLamHoKhau;
    }

    public void setNgayLamHoKhau(LocalDate ngayLamHoKhau) {
        this.ngayLamHoKhau = ngayLamHoKhau;
    }
}

