package main.java.sample.Controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ThongKeController2 {
    @FXML private TableView<Map<String, Object>> tableView;
    @FXML private TableColumn<Map<String, Object>, Object> colStt;
    @FXML private TableColumn<Map<String, Object>, Object> colState;
    @FXML private TableColumn<Map<String, Object>, Object> colName;
    @FXML private TableColumn<Map<String, Object>, Object> colTotalApartment;
    @FXML private TableColumn<Map<String, Object>, Object> colTotalFees;
    @FXML private TableColumn<Map<String, Object>, Object> colDay;
    @FXML private TextField txtGlobalSearch;

    private ObservableList<Map<String, Object>> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Thiết lập cột
        colStt.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get("Stt")));
        colState.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get("State")));
        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get("Name")));
        colTotalApartment.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get("TotalApartment")));
        colTotalFees.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get("TotalFees")));
        colDay.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get("Day")));


        // Tải dữ liệu ban đầu
        loadData();

        // Lọc theo từ khóa
        txtGlobalSearch.textProperty().addListener((obs, oldVal, newVal) -> filterData(newVal));
    }

    private void loadData() {
        masterData.clear();

        String query = """
            SELECT 
                kt.id,
                kt.tenkhoanthu,
                kt.batbuoc,
                kt.thoihan,
                COUNT(DISTINCT nt.hokhau_id) AS so_ho_dong,
                SUM(nt.sotien) AS tong_tien
            FROM khoanthu kt
            LEFT JOIN nop_tien nt ON kt.id = nt.khoanthu_id
            GROUP BY kt.id, kt.tenkhoanthu, kt.batbuoc, kt.thoihan
            ORDER BY kt.id
        """;

        try (Connection conn = database.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            int stt = 1;
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("Stt", stt++);
                row.put("State", rs.getBoolean("batbuoc") ? "Bắt buộc" : "Tự nguyện");
                row.put("Name", rs.getString("tenkhoanthu"));
                row.put("TotalApartment", rs.getInt("so_ho_dong"));
                row.put("TotalFees", rs.getDouble("tong_tien"));
                row.put("Day", rs.getDate("thoihan"));
                masterData.add(row);
            }

            tableView.setItems(masterData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void filterData(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            tableView.setItems(masterData);
            return;
        }

        ObservableList<Map<String, Object>> filtered = FXCollections.observableArrayList();
        String lowerKeyword = keyword.toLowerCase();

        for (Map<String, Object> item : masterData) {
            String name = String.valueOf(item.get("Name")).toLowerCase();
            String state = String.valueOf(item.get("State")).toLowerCase();

            if (name.contains(lowerKeyword) || state.contains(lowerKeyword)) {
                filtered.add(item);
            }
        }

        tableView.setItems(filtered);
    }

//    router
@FXML
private Label lblTrangChu;

    @FXML
    public void handleHomePageClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HomePage2.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblTrangChu.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
            stage.setMaximized(true);
            stage.setTitle("Quan ly Trang Chủ");
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
            stage.setMaximized(true);
            stage.setTitle("Quan ly Khoản Thu");
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
            stage.setMaximized(true);
            stage.setTitle("Quan ly Hộ Khẩu");
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
            stage.setMaximized(true);
            stage.setTitle("Quan ly Nhân Khẩu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi chuyển trang: " + e.getMessage());

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
            stage.setMaximized(true);
            stage.setTitle("Quan ly Nop Phi");
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
