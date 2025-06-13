package main.java.sample.Model;

import java.time.LocalDate;

public class NhanKhau {
    private int id;
    private String maHo;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String danToc;
    private String cccd;
    private String ngheNghiep;

    public NhanKhau(int id, String maHo, String hoTen, LocalDate ngaySinh, String gioiTinh, String danToc, String cccd, String ngheNghiep) {
        this.id = id;
        this.maHo = maHo;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.danToc = danToc;
        this.cccd = cccd;
        this.ngheNghiep = ngheNghiep;
    }

    public int getId() { return id; }
    public String getMaHo() { return maHo; }
    public String getHoTen() { return hoTen; }
    public LocalDate getNgaySinh() { return ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public String getDanToc() { return danToc; }
    public String getCccd() { return cccd; }
    public String getNgheNghiep() { return ngheNghiep; }
}
