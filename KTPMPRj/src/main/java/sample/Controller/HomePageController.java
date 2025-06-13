package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;


public class HomePageController {

    @FXML
    private Label lblTongSoHoKhau;

    @FXML
    private Label lblTongSoNhanKhau;


    @FXML
    public void initialize() {
        loadSummaryData();
        loadThongKeNhanKhauTheoHoKhau();
    }

    @FXML
    private LineChart<String, Number> lineChartNhanKhau;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private void loadThongKeNhanKhauTheoHoKhau() {
        try (Connection conn = database.DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String query = """
            SELECT hk.sohokhau, COUNT(tv.nhankhau_id) AS so_nhan_khau
            FROM hokhau hk
            LEFT JOIN thanhvien_hokhau tv ON hk.sohokhau = tv.hokhau_id
            GROUP BY hk.sohokhau
            ORDER BY hk.sohokhau
        """;

            ResultSet rs = stmt.executeQuery(query);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Số nhân khẩu theo hộ khẩu");

            while (rs.next()) {
                String sohokhau = rs.getString("sohokhau");
                int soNhanKhau = rs.getInt("so_nhan_khau");

                series.getData().add(new XYChart.Data<>(sohokhau, soNhanKhau));
            }

            lineChartNhanKhau.getData().clear();
            lineChartNhanKhau.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
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


        } catch (Exception e) {
            e.printStackTrace();
            lblTongSoHoKhau.setText("Lỗi");
            lblTongSoNhanKhau.setText("Lỗi");
        }
    }


    //    router button
    @FXML
    private Button btnNhanKhau;

    @FXML
    public void handleNhanKhauClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/NhanKhau.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) btnNhanKhau.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setMaximized(true);
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
            stage.setMaximized(true);
            stage.setTitle("Quan ly Nhân Khẩu");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HoKhau.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblHoKhau.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setMaximized(true);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HoKhau.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) btnHoKhau.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setMaximized(true);
            stage.setTitle("Quan lý Hộ Khẩu");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/ThongKe.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblThongKe.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setMaximized(true);
            stage.setTitle("Quan lý Thống Kê");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private Label lblTinhTrangLuuTru;

    @FXML
    public void handleTinhTrangClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/TinhTrangLuuTru.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblTinhTrangLuuTru.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setMaximized(true);
            stage.setTitle("Quan lý Tình Trạng Lưu Trú");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển trang: " + e.getMessage());
        }
    }


    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


}
