package main.java.sample.Model;


import java.sql.Date;

public class TamTruTamVang {
    private int id;
    private int nhanKhauId;
    private String trangThai;
    private String diaChiTamTruTamVang;
    private Date thoiGian;
    private String noiDungDeNghi;

    public TamTruTamVang() {
        // Constructor mặc định
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNhanKhauId() {
        return nhanKhauId;
    }

    public void setNhanKhauId(int nhanKhauId) {
        this.nhanKhauId = nhanKhauId;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getDiaChiTamTruTamVang() {
        return diaChiTamTruTamVang;
    }

    public void setDiaChiTamTruTamVang(String diaChiTamTruTamVang) {
        this.diaChiTamTruTamVang = diaChiTamTruTamVang;
    }

    public Date getThoiGian() {
        return thoiGian;
    }


    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNoiDungDeNghi() {
        return noiDungDeNghi;
    }

    public void setNoiDungDeNghi(String noiDungDeNghi) {
        this.noiDungDeNghi = noiDungDeNghi;
    }
}
