package main.java.sample.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.sample.Model.NopTien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NopPhiControlller {

    @FXML
    private TableView<NopTien> tableNopTien;

    @FXML
    private TableColumn<NopTien, String> colSoHoKhau;

    @FXML
    private TableColumn<NopTien, String> colKhoanThu;

    @FXML
    private TableColumn<NopTien, String> colNgayNop;

    @FXML
    private TableColumn<NopTien, String> colNguoiNop;

    @FXML
    private TableColumn<NopTien, String> colSoTien;

    private final ObservableList<NopTien> listNopTien = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupCellValueFactories();
        loadData("");
    }

    private void setupCellValueFactories() {
        colSoHoKhau.setCellValueFactory(cellData -> {
            int hoKhauId = cellData.getValue().getHoKhauId();
            String result = getHoTenHoKhauById(hoKhauId);
            return new SimpleStringProperty(result);
        });

        colKhoanThu.setCellValueFactory(cellData -> {
            int khoanThuId = cellData.getValue().getKhoanThuId();
            String result = getTenKhoanThuById(khoanThuId);
            return new SimpleStringProperty(result);
        });

        colNgayNop.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNgayNop().toString()));

        colNguoiNop.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNguoiNop()));

        colSoTien.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%,.0f đ", cellData.getValue().getSoTien())));

    }

    private String getHoTenHoKhauById(int hoKhauId) {
        String result = "";
        String sql = "SELECT sohokhau FROM hokhau WHERE sohokhau = ?";
        try (Connection conn = database.DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hoKhauId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString("sohokhau");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getTenKhoanThuById(int khoanThuId) {
        String result = "";
        String sql = "SELECT tenkhoanthu FROM khoanthu WHERE id = ?";
        try (Connection conn = database.DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, khoanThuId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString("tenkhoanthu");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void loadData(String keyword) {
        listNopTien.clear();
        String sql = "SELECT * FROM nop_tien";
        boolean hasKeyword = keyword != null && !keyword.isBlank();

        if (hasKeyword) {
            sql += " WHERE nguoinop LIKE ?";
        }

        try (Connection conn = database.DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (hasKeyword) {
                ps.setString(1, "%" + keyword.trim() + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NopTien nt = new NopTien();
                    nt.setId(rs.getInt("id"));
                    int hoKhauId = rs.getInt("hokhau_id");
                    nt.setHoKhauId(hoKhauId);
                    int khoanThuId = rs.getInt("khoanthu_id");
                    nt.setKhoanThuId(khoanThuId);
                    nt.setNgayNop(rs.getDate("ngaynop").toLocalDate());
                    nt.setNguoiNop(rs.getString("nguoinop"));
                    nt.setSoTien(rs.getDouble("sotien"));
                    listNopTien.add(nt);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableNopTien.setItems(listNopTien);
    }
        @FXML
        private TextField txtSearch;

        @FXML
        public void onSearch () {
            String keyword = txtSearch.getText();
            loadData(keyword);
        }

        @FXML
        public void onRefresh () {
            txtSearch.clear();
            loadData("");
        }
//        @FXML
//    private Button btnBack;


//        @FXML
//    private void onBackClicked() {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Controller/HomePage.fxml"));
//                Parent canHoPage = loader.load();
//
//                Stage stage = (Stage) btnBack.getScene().getWindow();
//                stage.setScene(new Scene(canHoPage));
//                stage.setTitle("Quan ly Trang Chủ");
//                stage.show();
//            } catch (Exception e) {
//                e.printStackTrace();
//                showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi chuyển trang: " + e.getMessage());
//
//            }
//        }

    @FXML
    private Label lblTrangChu;

    @FXML
    public void handleHomePageClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HomePage2.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblTrangChu.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Trang Chủ");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi chuyển trang: " + e.getMessage());

        }
    }

    @FXML
    private Label lblHoKhau;

    @FXML
    public void handleHoKhauClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HoKhau2.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblHoKhau.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Hộ Khẩu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi chuyển trang: " + e.getMessage());

        }
    }

    @FXML
    private Label lblThongKe;

    @FXML
    public void handleThongKeClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/ThongKe2.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblThongKe.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Thống Kê");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi chuyển trang: " + e.getMessage());

        }
    }

    @FXML
    private Label lblNhanKhau;

    @FXML
    public void handleNhanKhauClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/NhanKhau2.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblNhanKhau.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Nhân Khẩu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi chuyển trang: " + e.getMessage());

        }
    }

    @FXML
    private Label lblKhoanThu;

    @FXML
    public void handleKhoanThuClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/KhoanThu.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblKhoanThu.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Khoản Thu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi chuyển trang: " + e.getMessage());

        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    }