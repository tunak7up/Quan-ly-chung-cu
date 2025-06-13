package main.java.sample.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;

import java.math.BigDecimal;
import java.util.Optional;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.java.sample.Model.HoKhau;


import java.sql.*;
        import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HoKhauController {

    @FXML
    private TableView<HoKhau> tableHouseholds;  // Bổ sung khai báo TableView

    @FXML
    private TableColumn<HoKhau, Integer> colSoHoKhau;

    @FXML
    private TableColumn<HoKhau, String> colSoNha;

    @FXML
    private TableColumn<HoKhau, BigDecimal> colDienTich;

    @FXML
    private TableColumn<HoKhau, String> colDuong;

    @FXML
    private TableColumn<HoKhau, String> colPhuong;

    @FXML
    private TableColumn<HoKhau, String> colQuan;

    @FXML
    private TableColumn<HoKhau, LocalDate> colNgayLamHoKhau;

    @FXML
    private TextField txtSearch;

    private ObservableList<HoKhau> hoKhauList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colSoHoKhau.setCellValueFactory(new PropertyValueFactory<>("soHoKhau"));
        colSoNha.setCellValueFactory(new PropertyValueFactory<>("soNha"));
        colDienTich.setCellValueFactory(new PropertyValueFactory<>("area"));
        colDuong.setCellValueFactory(new PropertyValueFactory<>("duong"));
        colPhuong.setCellValueFactory(new PropertyValueFactory<>("phuong"));
        colQuan.setCellValueFactory(new PropertyValueFactory<>("quan"));
        colNgayLamHoKhau.setCellValueFactory(new PropertyValueFactory<>("ngayLamHoKhau"));

        // Format hiển thị ngày sinh (LocalDate)
        colNgayLamHoKhau.setCellFactory(column -> new TableCell<HoKhau, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formatter.format(item));
                }
            }
        });
        addButtonToTable();
        loadData();
    }
    @FXML
    private void loadData() {
        hoKhauList.clear();
        String sql = "SELECT * from chungcu.hokhau";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int sohokhau = rs.getInt("sohokhau");
                String sonha = rs.getString("sonha");
                String duong = rs.getString("duong");
                String phuong = rs.getString("phuong");
                String quan = rs.getString("quan");
                Date sqlDate = rs.getDate("ngaylamhokhau");
                LocalDate ngayLamHoKhau = sqlDate != null ? sqlDate.toLocalDate() : null;
                BigDecimal area = rs.getBigDecimal("area");

                HoKhau nk = new HoKhau(sohokhau, sonha, duong, phuong, quan, ngayLamHoKhau, area);
                hoKhauList.add(nk);
            }

            tableHouseholds.setItems(hoKhauList);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi truy vấn", "Không thể tải dữ liệu hộ khẩu.");
        }
    }

    @FXML
    private void onSearch(ActionEvent event) {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            tableHouseholds.setItems(hoKhauList);
            return;
        }

        ObservableList<HoKhau> filteredList = FXCollections.observableArrayList();
        for (HoKhau nk : hoKhauList) {
            if (String.valueOf(nk.getSoHoKhau()).toLowerCase().contains(keyword.toLowerCase()) ||
                    (nk.getSoNha() != null && nk.getSoNha().toLowerCase().contains(keyword)) ||
                    (nk.getDuong() != null && nk.getDuong().toLowerCase().contains(keyword)) ||
                    (nk.getPhuong() != null && nk.getPhuong().toLowerCase().contains(keyword)) ||
                    (nk.getQuan() != null && nk.getQuan().toLowerCase().contains(keyword))) {
                filteredList.add(nk);
            }
        }
        tableHouseholds.setItems(filteredList);
    }

    @FXML
    private void onRefresh(ActionEvent event) {
        txtSearch.clear();
        loadData();
    }


    @FXML
    private TableColumn<HoKhau, Void> colAction;
    @FXML
    private void addButtonToTable() {
        colAction.setCellFactory(param -> new TableCell<HoKhau, Void>() {
            private final Button btnEdit = new Button("Sửa");
            private final Button btnDelete = new Button("Xóa");
            private final HBox pane = new HBox(10, btnEdit, btnDelete);

            {
                btnEdit.setOnAction(event -> {
                    HoKhau nk = getTableView().getItems().get(getIndex());
                    onEdit(nk);
                });

                btnDelete.setOnAction(event -> {
                    HoKhau nk = getTableView().getItems().get(getIndex());
                    onDelete(nk);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }
    @FXML
    private void onEdit(HoKhau hk) {
        // Hiển thị dialog sửa thông tin hoặc chuyển sang màn hình sửa
        Dialog<HoKhau> dialog = new Dialog<>();
        dialog.setTitle("Sưa hộ khẩu");
        dialog.setHeaderText("Cập nhật thông tin hộ khẩu");

        ButtonType saveBtn = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField soHoKhauField = new TextField(String.valueOf(hk.getSoHoKhau()));
        soHoKhauField.setDisable(true); // không cho phép chỉnh sửa số hộ khẩu
        TextField soNhaField = new TextField(hk.getSoNha());
        TextField areaField = new TextField(hk.getArea().toPlainString());
        TextField duongField = new TextField(hk.getDuong());
        TextField phuongField = new TextField(hk.getPhuong());
        TextField quanField = new TextField(hk.getQuan());
        DatePicker ngayLamHoKhauPicker = new DatePicker(hk.getNgayLamHoKhau());

        grid.add(new Label("Số hộ khẩu:"), 0, 0);
        grid.add(soHoKhauField, 1, 0);
        grid.add(new Label("Số nhà:"), 0, 1);
        grid.add(soNhaField, 1, 1);
        grid.add(new Label("Diện tích:"), 0, 2);
        grid.add(areaField, 1, 2);
        grid.add(new Label("Đường:"), 0, 2);
        grid.add(duongField, 1, 2);
        grid.add(new Label("Phường:"), 0, 3);
        grid.add(phuongField, 1, 3);
        grid.add(new Label("Quận:"), 0, 4);
        grid.add(quanField, 1, 4);
        grid.add(new Label("Ngày làm hộ khẩu:"), 0, 5);
        grid.add(ngayLamHoKhauPicker, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveBtn) {
                try {
                    return new HoKhau(
                            hk.getSoHoKhau(),
                            soNhaField.getText().trim(),
                            duongField.getText().trim(),
                            phuongField.getText().trim(),
                            quanField.getText().trim(),
                            ngayLamHoKhauPicker.getValue(),
                            new BigDecimal(areaField.getText().trim())
                    );
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi dữ liệu", "Vui lòng kiểm tra lại thông tin nhập.");
                    return null;
                }
            }
            return null;
        });

        Optional<HoKhau> result = dialog.showAndWait();

        result.ifPresent(updatedHK -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE hokhau SET sonha=?, area=?, duong=?, phuong=?, quan=?, ngaylamhokhau=? WHERE sohokhau=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, updatedHK.getSoNha());
                ps.setBigDecimal(2, updatedHK.getArea());
                ps.setString(3, updatedHK.getDuong());
                ps.setString(4, updatedHK.getPhuong());
                ps.setString(5, updatedHK.getQuan());
                ps.setDate(6, Date.valueOf(updatedHK.getNgayLamHoKhau()));
                ps.setInt(7, updatedHK.getSoHoKhau());

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Thông tin hộ khẩu đã được cập nhật.");
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Lỗi cập nhật", "Không thể cập nhật hộ khẩu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi SQL", "Không thể cập nhật: " + e.getMessage());
            }
        });
    }
    @FXML
    private void onDelete(HoKhau nk) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xóa nhân khẩu");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa nhân khẩu ID: " + nk.getSoHoKhau() + "?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Xóa dữ liệu trong database
                try (Connection conn = DatabaseConnection.getConnection()) {

                    String sql1 = "DELETE FROM hokhau WHERE sohokhau = ?";
                    PreparedStatement ps1 = conn.prepareStatement(sql1);
                    ps1.setInt(1, nk.getSoHoKhau());
                    int affectedRows = ps1.executeUpdate();

                    if (affectedRows > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Xóa hộ khẩu", "Đã xóa hộ khẩu ID: " + nk.getSoHoKhau());
                        loadData();  // Cập nhật lại bảng
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Lỗi xóa", "Không tìm thấy hộ khẩu để xóa.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Lỗi DB", "Xóa hộ khẩu thất bại: " + e.getMessage());
                }
            }
        });
    }


    @FXML
    private void onAdd(ActionEvent event) {
        Dialog<HoKhau> dialog = new Dialog<>();
        dialog.setTitle("Thêm mới hộ khẩu");
        dialog.setHeaderText("Nhập thông tin hộ khẩu");

        ButtonType addButtonType = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField soHoKhauField = new TextField();
        soHoKhauField.setPromptText("Số hộ khẩu");
        TextField soNhaField = new TextField();
        soNhaField.setPromptText("Số nhà");
        TextField dienTichField = new TextField();
        dienTichField.setPromptText("Diện tích");
        TextField duongField = new TextField();
        duongField.setPromptText("Đường");
        TextField phuongField = new TextField();
        phuongField.setPromptText("Phường");
        TextField quanField = new TextField();
        quanField.setPromptText("Quận");

        DatePicker ngayLamHoKhauPicker = new DatePicker();
        ngayLamHoKhauPicker.setPromptText("Ngày làm hộ khẩu");

        grid.add(new Label("Số hộ khẩu:"), 0, 0);
        grid.add(soHoKhauField, 1, 0);
        grid.add(new Label("Số nhà:"), 0, 1);
        grid.add(soNhaField, 1, 1);
        grid.add(new Label("Đường:"), 0, 2);
        grid.add(duongField, 1, 2);
        grid.add(new Label("Phường:"), 0, 3);
        grid.add(phuongField, 1, 3);
        grid.add(new Label("Quận:"), 0, 4);
        grid.add(quanField, 1, 4);
        grid.add(new Label("Ngày làm hộ khẩu:"), 0, 5);
        grid.add(ngayLamHoKhauPicker, 1, 5);
        grid.add(new Label("Diện tích:"), 0, 6);
        grid.add(dienTichField, 1, 6);


        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    int soHoKhau = Integer.parseInt(soHoKhauField.getText().trim());
                    String soNha = soNhaField.getText().trim();
                    BigDecimal dienTich = new BigDecimal(dienTichField.getText().trim());
                    String duong = duongField.getText().trim();
                    String phuong = phuongField.getText().trim();
                    String quan = quanField.getText().trim();
                    LocalDate ngayLamHoKhau = ngayLamHoKhauPicker.getValue();

                    return new HoKhau(soHoKhau, soNha, duong, phuong, quan, ngayLamHoKhau, dienTich);
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Vui lòng kiểm tra lại định dạng dữ liệu.");
                    return null;
                }
            }
            return null;
        });


        Optional<HoKhau> result = dialog.showAndWait();

        result.ifPresent(nk -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO hokhau (sohokhau, sonha, duong, phuong, quan, ngaylamhokhau, area) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, nk.getSoHoKhau());
                ps.setString(2, nk.getSoNha());
                ps.setString(3, nk.getDuong());
                ps.setString(4, nk.getPhuong());
                ps.setString(5, nk.getQuan());
                ps.setDate(6, Date.valueOf(nk.getNgayLamHoKhau()));
                ps.setBigDecimal(7, nk.getArea());
                ps.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm hộ khẩu mới!");
                loadData();

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi DB", "Không thể thêm hộ khẩu mới: " + e.getMessage());
            }
        });
    }


    //router
    @FXML
    private Label lblTrangChu;

    @FXML
    public void handleHomePageClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HomePage.fxml"));
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
    private Label lblNhanKhau;

    @FXML
    public void handleNhanKhauClick() {
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
            stage.setTitle("Quan ly Thống Kê");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Lỗi chuyển trang: " + e.getMessage());

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
            stage.setTitle("Quan ly Thống Kê");
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

