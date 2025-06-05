package main.java.sample.Model;

import java.time.LocalDate;

public class KhoanThu {
    private int id;
    private LocalDate ngayTao;
    private LocalDate thoiHan;
    private String tenKhoanThu;
    private boolean batBuoc;
    private String ghiChu;

    public KhoanThu() {
        // Constructor mặc định
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(LocalDate ngayTao) {
        this.ngayTao = ngayTao;
    }

    public LocalDate getThoiHan() {
        return thoiHan;
    }

    public void setThoiHan(LocalDate thoiHan) {
        this.thoiHan = thoiHan;
    }

    public String getTenKhoanThu() {
        return tenKhoanThu;
    }

    public void setTenKhoanThu(String tenKhoanThu) {
        this.tenKhoanThu = tenKhoanThu;
    }

    public boolean isBatBuoc() {
        return batBuoc;
    }

    public void setBatBuoc(boolean batBuoc) {
        this.batBuoc = batBuoc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
