package main.java.sample.Model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class LichSuHoKhau {
    private IntegerProperty id;
    private IntegerProperty hoKhauId;
    private IntegerProperty nhanKhauId;
    private StringProperty loaiThayDoi;
    private ObjectProperty<LocalDate> thoiGian;

    public LichSuHoKhau() {
        this.id = new SimpleIntegerProperty();
        this.hoKhauId = new SimpleIntegerProperty();
        this.nhanKhauId = new SimpleIntegerProperty();
        this.loaiThayDoi = new SimpleStringProperty();
        this.thoiGian = new SimpleObjectProperty<>();
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public int getHoKhauId() { return hoKhauId.get(); }
    public void setHoKhauId(int hoKhauId) { this.hoKhauId.set(hoKhauId); }
    public IntegerProperty hoKhauIdProperty() { return hoKhauId; }

    public int getNhanKhauId() { return nhanKhauId.get(); }
    public void setNhanKhauId(int nhanKhauId) { this.nhanKhauId.set(nhanKhauId); }
    public IntegerProperty nhanKhauIdProperty() { return nhanKhauId; }

    public String getLoaiThayDoi() { return loaiThayDoi.get(); }
    public void setLoaiThayDoi(String loaiThayDoi) { this.loaiThayDoi.set(loaiThayDoi); }
    public StringProperty loaiThayDoiProperty() { return loaiThayDoi; }

    public LocalDate getThoiGian() { return thoiGian.get(); }
    public void setThoiGian(LocalDate thoiGian) { this.thoiGian.set(thoiGian); }
    public ObjectProperty<LocalDate> thoiGianProperty() { return thoiGian; }
}
