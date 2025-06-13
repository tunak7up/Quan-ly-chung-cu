package main.java.sample.Controller;

import database.DatabaseConnection; // import class của bạn
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class ThuPhiController {

    @FXML
    private TextField tenKhoanThuField;

    @FXML
    private TextField tenNguoiNopField;

    @FXML
    private Button chonKhoanThuButton;

    @FXML
    private Button thuPhiButton;

    @FXML
    private DatePicker ngayNopDatePicker;

    @FXML
    private TextField soTienField;

    @FXML
    private Button quayLaiBtn;

    private Connection connection;

    public void initialize() {
        connection = DatabaseConnection.getConnection();
        if (connection == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi kết nối", "Không thể kết nối đến cơ sở dữ liệu.");
        }
    }

    @FXML
    private void onQuayLaiClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HomePage2.fxml"));
            Parent homePage = loader.load();

            Stage stage = (Stage) quayLaiBtn.getScene().getWindow();
            stage.setScene(new Scene(homePage));
            stage.setTitle("Quan lý Home Page");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi chuyển trang: " + e.getMessage());
        }
    }

    @FXML
    private void onChonKhoanThuClicked(ActionEvent event) {
        if (connection == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Chưa có kết nối cơ sở dữ liệu.");
            return;
        }
        try {
            ObservableList<String> listKhoanThu = FXCollections.observableArrayList();

            String sql = "SELECT tenkhoanthu FROM khoanthu";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                listKhoanThu.add(rs.getString("tenkhoanthu"));
            }

            rs.close();
            stmt.close();

            ListView<String> listView = new ListView<>(listKhoanThu);
            listView.setPrefSize(300, 200);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Chọn khoản thu");

            Button selectButton = new Button("Chọn");
            selectButton.setDisable(true);

            VBox vbox = new VBox(10, listView, selectButton);
            vbox.setPadding(new javafx.geometry.Insets(10));

            listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                selectButton.setDisable(newVal == null);
            });

            selectButton.setOnAction(e -> {
                String selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    tenKhoanThuField.setText(selected);
                    popupStage.close();
                }
            });

            Scene scene = new Scene(vbox);
            popupStage.setScene(scene);
            popupStage.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi truy vấn", e.getMessage());
        }
    }

    @FXML
    private void onChonNguoiNopClick(ActionEvent event) {
        if(connection == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", " Chưa kết nối được database");
        }
        try {
            ObservableList<String> listNguoiNop = FXCollections.observableArrayList();
            String sql = "SELECT hoten from nhankhau";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                listNguoiNop.add(rs.getString("hoten"));
            }

            rs.close();
            stmt.close();

            ListView<String> listView = new ListView<>(listNguoiNop);
            listView.setPrefSize(300, 200);

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Chọn người nộp");

            Button selectButton = new Button("Chọn");
            selectButton.setDisable(true);

            VBox vbox = new VBox(10, listView, selectButton);
            vbox.setPadding(new javafx.geometry.Insets(10));

            listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                selectButton.setDisable(newVal == null);
            });

            selectButton.setOnAction(e -> {
                String selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    tenNguoiNopField.setText(selected);
                    popupStage.close();
                }
            });

            Scene scene = new Scene(vbox);
            popupStage.setScene(scene);
            popupStage.showAndWait();
        } catch(SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi truy vấn", e.getMessage());
        }
    }

    @FXML
    private void onThuPhiClicked(ActionEvent event) {
        if(connection == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Chưa kết nối được database");
            return;
        }
        try {
            String tenKhoanThu = tenKhoanThuField.getText();
            String tenNguoiNop = tenNguoiNopField.getText();
            String soTien = soTienField.getText();
            if(tenKhoanThu.isEmpty() || tenNguoiNop.isEmpty() || soTien.isEmpty() ||ngayNopDatePicker.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Thiếu dữ liệu", "Vui lòng nhập đầy đủ thông tin.");
                return;
            }
            Date ngayNop = Date.valueOf(ngayNopDatePicker.getValue());
            double soTienDouble;
            try {
                soTienDouble = Double.parseDouble(soTien);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Số tiền phải là số hợp lệ.");
                return;
            }

            // Lấy khoanthu_id
            int khoanthu_id = -1;
            String sqlKhoanThu = "SELECT id FROM khoanthu WHERE tenkhoanthu = ?";
            PreparedStatement pstKhoanThu = connection.prepareStatement(sqlKhoanThu);
            pstKhoanThu.setString(1, tenKhoanThu);
            ResultSet rsKhoanThu = pstKhoanThu.executeQuery();
            if(rsKhoanThu.next()) {
                khoanthu_id = rsKhoanThu.getInt("id");
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không tìm thấy khoản thu.");
                rsKhoanThu.close();
                pstKhoanThu.close();
                return;
            }
            rsKhoanThu.close();
            pstKhoanThu.close();

            // Lấy hokhau_id theo tên người nộp (hoten trong nhankhau -> tìm hokhau_id từ thanhvien_hokhau)
            int hokhau_id = -1;
            String sqlHoKhau = "SELECT hokhau.sohokhau " +
                    "FROM hokhau JOIN thanhvien_hokhau ON hokhau.sohokhau = thanhvien_hokhau.hokhau_id " +
                    "JOIN nhankhau ON nhankhau.id = thanhvien_hokhau.nhankhau_id " +
                    "WHERE nhankhau.hoten = ?";
            PreparedStatement pstHoKhau = connection.prepareStatement(sqlHoKhau);
            pstHoKhau.setString(1, tenNguoiNop);
            ResultSet rsHoKhau = pstHoKhau.executeQuery();
            if(rsHoKhau.next()) {
                hokhau_id = rsHoKhau.getInt("sohokhau");
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không tìm thấy hộ khẩu của người nộp.");
                rsHoKhau.close();
                pstHoKhau.close();
                return;
            }
            rsHoKhau.close();
            pstHoKhau.close();

            // Chèn dữ liệu vào nop_tien
            String sqlInsert = "INSERT INTO nop_tien (hokhau_id, khoanthu_id, ngaynop, nguoinop, sotien) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstInsert = connection.prepareStatement(sqlInsert);
            pstInsert.setInt(1, hokhau_id);
            pstInsert.setInt(2, khoanthu_id);
            pstInsert.setDate(3, ngayNop);
            pstInsert.setString(4, tenNguoiNop);
            pstInsert.setDouble(5, soTienDouble);

            int rows = pstInsert.executeUpdate();
            pstInsert.close();

            if(rows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Lưu thông tin thu phí thành công.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Lưu thông tin thu phí thất bại.");
            }

        } catch(Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi khi lưu dữ liệu: " + e.getMessage());
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
