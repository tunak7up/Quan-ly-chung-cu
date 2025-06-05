package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class HomePageController2 {

    @FXML
    private Label lblTongSoHoKhau;

    @FXML
    private Label lblTongSoNhanKhau;

    @FXML
    private Label lblTongSoKhoanThu;

    @FXML
    public void initialize() {
        loadSummaryData();
    }

    private void loadSummaryData() {
        try (Connection conn = database.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Đếm số hộ khẩu
            ResultSet rs1 = stmt.executeQuery("SELECT COUNT(*) FROM hokhau");
            if (rs1.next()) {
                int countHoKhau = rs1.getInt(1);
                lblTongSoHoKhau.setText("Tổng số: " + countHoKhau);
            }

            // Đếm số nhân khẩu
            ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM nhankhau");
            if (rs2.next()) {
                int countNhanKhau = rs2.getInt(1);
                lblTongSoNhanKhau.setText("Tổng số: " + countNhanKhau);
            }

            // Đếm số khoản thu
            ResultSet rs3 = stmt.executeQuery("SELECT COUNT(*) FROM khoanthu");
            if (rs3.next()) {
                int countKhoanThu = rs3.getInt(1);
                lblTongSoKhoanThu.setText("Tổng số: " + countKhoanThu);
            }

        } catch (Exception e) {
            e.printStackTrace();
            lblTongSoHoKhau.setText("Lỗi");
            lblTongSoNhanKhau.setText("Lỗi");
            lblTongSoKhoanThu.setText("Lỗi");
        }
    }


    //    router button
    @FXML
    private Button btnNhanKhau;

    @FXML
    public void handleNhanKhauClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/NhanKhau2.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) btnNhanKhau.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Nhân Khẩu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private Button btnKhoanThu;

    @FXML
    public void handleKhoanThuClickBtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/KhoanThu.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) btnKhoanThu.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Khoản Thu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }


    //    router
    @FXML
    private Label lblNhanKhau;

    @FXML
    public void handleNhanKhauClickBtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/NhanKhau.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblNhanKhau.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan ly Nhân Khẩu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
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
            showAlert("Lỗi chuyển trang: " + e.getMessage());
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
            stage.setTitle("Quan lý Hộ Khẩu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private Button btnHoKhau;

    @FXML
    public void handleHoKhauClickBtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HoKhau2.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) btnHoKhau.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan lý Hộ Khẩu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private Button btnNopPhi;

    @FXML
    public void handleNopPhiClickBtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/ThuPhi.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) btnNopPhi.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Nộp Phí");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private Button btnDSNopPhi;

    @FXML
    public void handleDSNopPhiClickBtn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/NopPhi.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) btnDSNopPhi.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Nộp Phí");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
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
            stage.setTitle("Quan lý Thống Kê");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private Label lblNopPhi;

    @FXML
    public void handleNopPhiClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/NopPhi.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblNopPhi.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setTitle("Quan lý Thống Kê");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

//    @FXML
//    private Label lblTinhTrangLuuTru;
//
//    @FXML
//    public void handleTinhTrangClick() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/TinhTrangLuuTru.fxml"));
//            Parent canHoPage = loader.load();
//
//            Stage stage = (Stage) lblTinhTrangLuuTru.getScene().getWindow();
//            stage.setScene(new Scene(canHoPage));
//            stage.setTitle("Quan lý Tình Trạng Lưu Trú");
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            showAlert("Lỗi chuyển trang: " + e.getMessage());
//        }
//    }


    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


}
