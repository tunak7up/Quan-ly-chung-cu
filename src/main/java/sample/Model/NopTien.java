package main.java.sample.Model;

import java.time.LocalDate;

public class NopTien {
    private int id;
    private int hoKhauId;
    private int khoanThuId;
    private LocalDate ngayNop;
    private String nguoiNop;
    private double soTien;

    public NopTien(int id, int hoKhauId, int khoanThuId, LocalDate ngayNop, String nguoiNop, double soTien) {
        this.id = id;
        this.hoKhauId = hoKhauId;
        this.khoanThuId = khoanThuId;
        this.ngayNop = ngayNop;
        this.nguoiNop = nguoiNop;
        this.soTien = soTien;
    }

    public NopTien() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getHoKhauId() { return hoKhauId; }
    public void setHoKhauId(int hoKhauId) { this.hoKhauId = hoKhauId; }

    public int getKhoanThuId() { return khoanThuId; }
    public void setKhoanThuId(int khoanThuId) { this.khoanThuId = khoanThuId; }

    public LocalDate getNgayNop() { return ngayNop; }
    public void setNgayNop(LocalDate ngayNop) { this.ngayNop = ngayNop; }

    public String getNguoiNop() { return nguoiNop; }
    public void setNguoiNop(String nguoiNop) { this.nguoiNop = nguoiNop; }

    public double getSoTien() { return soTien; }
    public void setSoTien(double soTien) { this.soTien = soTien; }
}
