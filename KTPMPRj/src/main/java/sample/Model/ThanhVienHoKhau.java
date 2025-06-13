package main.java.sample.Model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class ThanhVienHoKhau {
    private IntegerProperty hoKhauId;
    private IntegerProperty nhanKhauId;
    private ObjectProperty<LocalDate> ngayThem;
    private StringProperty quanHeVoiChuHo;

    public ThanhVienHoKhau() {
        this.hoKhauId = new SimpleIntegerProperty();
        this.nhanKhauId = new SimpleIntegerProperty();
        this.ngayThem = new SimpleObjectProperty<>();
        this.quanHeVoiChuHo = new SimpleStringProperty();
    }

    public int getHoKhauId() { return hoKhauId.get(); }
    public void setHoKhauId(int hoKhauId) { this.hoKhauId.set(hoKhauId); }
    public IntegerProperty hoKhauIdProperty() { return hoKhauId; }

    public int getNhanKhauId() { return nhanKhauId.get(); }
    public void setNhanKhauId(int nhanKhauId) { this.nhanKhauId.set(nhanKhauId); }
    public IntegerProperty nhanKhauIdProperty() { return nhanKhauId; }

    public LocalDate getNgayThem() { return ngayThem.get(); }
    public void setNgayThem(LocalDate ngayThem) { this.ngayThem.set(ngayThem); }
    public ObjectProperty<LocalDate> ngayThemProperty() { return ngayThem; }

    public String getQuanHeVoiChuHo() { return quanHeVoiChuHo.get(); }
    public void setQuanHeVoiChuHo(String quanHeVoiChuHo) { this.quanHeVoiChuHo.set(quanHeVoiChuHo); }
    public StringProperty quanHeVoiChuHoProperty() { return quanHeVoiChuHo; }
}

