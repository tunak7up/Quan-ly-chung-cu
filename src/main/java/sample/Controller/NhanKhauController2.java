package main.java.sample.Controller;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.java.sample.Model.NhanKhau;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class NhanKhauController2 {

    @FXML
    private TableView<NhanKhau> tableResidents;  // Bổ sung khai báo TableView

    @FXML
    private TableColumn<NhanKhau, Integer> colID;

    @FXML
    private TableColumn<NhanKhau, String> colMaHo;

    @FXML
    private TableColumn<NhanKhau, String> colHoTen;

    @FXML
    private TableColumn<NhanKhau, LocalDate> colNgaySinh;

    @FXML
    private TableColumn<NhanKhau, String> colGioiTinh;

    @FXML
    private TableColumn<NhanKhau, String> colDanToc;

    @FXML
    private TableColumn<NhanKhau, String> colCCCD;

    @FXML
    private TableColumn<NhanKhau, String> colNgheNghiep;

    @FXML
    private TextField txtSearch;

    private ObservableList<NhanKhau> nhanKhauList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMaHo.setCellValueFactory(new PropertyValueFactory<>("maHo"));
        colHoTen.setCellValueFactory(new PropertyValueFactory<>("hoTen"));
        colNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        colGioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        colDanToc.setCellValueFactory(new PropertyValueFactory<>("danToc"));
        colCCCD.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        colNgheNghiep.setCellValueFactory(new PropertyValueFactory<>("ngheNghiep"));

        // Format hiển thị ngày sinh (LocalDate)
        colNgaySinh.setCellFactory(column -> new TableCell<NhanKhau, LocalDate>() {
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

    private void loadData() {
        nhanKhauList.clear();
        String sql = "SELECT nk.id, hk.sohokhau, nk.hoten, nk.ngaysinh, nk.gioitinh, nk.dantoc, nk.cccd, nk.nghenghiep " +
                "FROM nhankhau nk " +
                "LEFT JOIN thanhvien_hokhau tv ON nk.id = tv.nhankhau_id " +
                "LEFT JOIN hokhau hk ON tv.hokhau_id = hk.sohokhau";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String maHo = rs.getString("sohokhau");
                String hoTen = rs.getString("hoten");
                Date sqlDate = rs.getDate("ngaysinh");
                LocalDate ngaySinh = sqlDate != null ? sqlDate.toLocalDate() : null;
                String gioiTinh = rs.getString("gioitinh");
                String danToc = rs.getString("dantoc");
                String cccd = rs.getString("cccd");
                String ngheNghiep = rs.getString("nghenghiep");

                NhanKhau nk = new NhanKhau(id, maHo, hoTen, ngaySinh, gioiTinh, danToc, cccd, ngheNghiep);
                nhanKhauList.add(nk);
            }

            tableResidents.setItems(nhanKhauList);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi truy vấn", "Không thể tải dữ liệu nhân khẩu.");
        }
    }

    @FXML
    private void onSearch(ActionEvent event) {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            tableResidents.setItems(nhanKhauList);
            return;
        }

        ObservableList<NhanKhau> filteredList = FXCollections.observableArrayList();
        for (NhanKhau nk : nhanKhauList) {
            if (nk.getHoTen().toLowerCase().contains(keyword) ||
                    (nk.getMaHo() != null && nk.getMaHo().toLowerCase().contains(keyword)) ||
                    (nk.getCccd() != null && nk.getCccd().toLowerCase().contains(keyword))) {
                filteredList.add(nk);
            }
        }
        tableResidents.setItems(filteredList);
    }

    @FXML
    private void onRefresh(ActionEvent event) {
        txtSearch.clear();
        loadData();
    }


    @FXML
    private TableColumn<NhanKhau, Void> colAction;

    private void addButtonToTable() {
        colAction.setCellFactory(param -> new TableCell<NhanKhau, Void>() {
            private final Button btnEdit = new Button("Sửa");
            private final Button btnDelete = new Button("Xóa");
            private final HBox pane = new HBox(10, btnEdit, btnDelete);

            {
                btnEdit.setOnAction(event -> {
                    NhanKhau nk = getTableView().getItems().get(getIndex());
                    onEdit(nk);
                });

                btnDelete.setOnAction(event -> {
                    NhanKhau nk = getTableView().getItems().get(getIndex());
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

    private void onEdit(NhanKhau nk) {
        Dialog<NhanKhau> dialog = new Dialog<>();
        dialog.setTitle("Sửa nhân khẩu");
        dialog.setHeaderText("Cập nhật thông tin nhân khẩu");

        ButtonType saveButtonType = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField maHoField = new TextField(nk.getMaHo());
        TextField hoTenField = new TextField(nk.getHoTen());
        TextField ngaySinhField = new TextField(nk.getNgaySinh() != null ? nk.getNgaySinh().toString() : "");

        ChoiceBox<String> gioiTinhChoice = new ChoiceBox<>();
        gioiTinhChoice.getItems().addAll("Nam", "Nu");
        gioiTinhChoice.setValue(nk.getGioiTinh());

        TextField danTocField = new TextField(nk.getDanToc());
        TextField cccdField = new TextField(nk.getCccd());
        TextField ngheNghiepField = new TextField(nk.getNgheNghiep());

        grid.add(new Label("Mã hộ:"), 0, 0);
        grid.add(maHoField, 1, 0);
        grid.add(new Label("Họ tên:"), 0, 1);
        grid.add(hoTenField, 1, 1);
        grid.add(new Label("Ngày sinh (yyyy-MM-dd):"), 0, 2);
        grid.add(ngaySinhField, 1, 2);
        grid.add(new Label("Giới tính:"), 0, 3);
        grid.add(gioiTinhChoice, 1, 3);
        grid.add(new Label("Dân tộc:"), 0, 4);
        grid.add(danTocField, 1, 4);
        grid.add(new Label("CCCD:"), 0, 5);
        grid.add(cccdField, 1, 5);
        grid.add(new Label("Nghề nghiệp:"), 0, 6);
        grid.add(ngheNghiepField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Convert the result when Save is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    LocalDate ngaySinh = LocalDate.parse(ngaySinhField.getText().trim());
                    return new NhanKhau(nk.getId(),
                            maHoField.getText().trim(),
                            hoTenField.getText().trim(),
                            ngaySinh,
                            gioiTinhChoice.getValue(),
                            danTocField.getText().trim(),
                            cccdField.getText().trim(),
                            ngheNghiepField.getText().trim());
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Ngày sinh phải đúng định dạng yyyy-MM-dd");
                    return null;
                }
            }
            return null;
        });

        Optional<NhanKhau> result = dialog.showAndWait();

        result.ifPresent(updatedNk -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE nhankhau SET hoten=?, ngaysinh=?, gioitinh=?, dantoc=?, cccd=?, nghenghiep=? WHERE id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, updatedNk.getHoTen());
                ps.setDate(2, Date.valueOf(updatedNk.getNgaySinh()));
                ps.setString(3, updatedNk.getGioiTinh());
                ps.setString(4, updatedNk.getDanToc());
                ps.setString(5, updatedNk.getCccd());
                ps.setString(6, updatedNk.getNgheNghiep());
                ps.setInt(7, updatedNk.getId());
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật nhân khẩu thành công!");
                    loadData();  // Reload dữ liệu mới
                } else {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Cập nhật nhân khẩu thất bại!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi DB", "Lỗi khi cập nhật: " + e.getMessage());
            }
        });
    }


    private void onDelete(NhanKhau nk) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xóa nhân khẩu");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa nhân khẩu ID: " + nk.getId() + "?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Xóa dữ liệu trong database
                try (Connection conn = DatabaseConnection.getConnection()) {
                    // Trước tiên xóa trong bảng thanhvien_hokhau (bảng liên kết)
                    String sql1 = "DELETE FROM thanhvien_hokhau WHERE nhankhau_id = ?";
                    PreparedStatement ps1 = conn.prepareStatement(sql1);
                    ps1.setInt(1, nk.getId());
                    ps1.executeUpdate();

                    // Xóa trong bảng nhankhau
                    String sql2 = "DELETE FROM nhankhau WHERE id = ?";
                    PreparedStatement ps2 = conn.prepareStatement(sql2);
                    ps2.setInt(1, nk.getId());
                    int affectedRows = ps2.executeUpdate();

                    if (affectedRows > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Xóa nhân khẩu", "Đã xóa nhân khẩu ID: " + nk.getId());
                        loadData();  // Cập nhật lại bảng
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Lỗi xóa", "Không tìm thấy nhân khẩu để xóa.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Lỗi DB", "Xóa nhân khẩu thất bại: " + e.getMessage());
                }
            }
        });
    }


    @FXML
    private void onAdd(ActionEvent event) {
        Dialog<NhanKhau> dialog = new Dialog<>();
        dialog.setTitle("Thêm mới nhân khẩu");
        dialog.setHeaderText("Nhập thông tin nhân khẩu");

        ButtonType addButtonType = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField maHoField = new TextField();
        maHoField.setPromptText("Mã hộ");
        TextField hoTenField = new TextField();
        hoTenField.setPromptText("Họ tên");
        TextField ngaySinhField = new TextField();
        ngaySinhField.setPromptText("Ngày sinh (yyyy-MM-dd)");

        // Thay TextField giới tính bằng ChoiceBox
        ChoiceBox<String> gioiTinhChoice = new ChoiceBox<>();
        gioiTinhChoice.getItems().addAll("Nam", "Nu");
        gioiTinhChoice.getSelectionModel().selectFirst(); // Mặc định chọn "Nam"

        TextField danTocField = new TextField();
        danTocField.setPromptText("Dân tộc");
        TextField cccdField = new TextField();
        cccdField.setPromptText("CCCD");
        TextField ngheNghiepField = new TextField();
        ngheNghiepField.setPromptText("Nghề nghiệp");

        grid.add(new Label("Mã hộ:"), 0, 0);
        grid.add(maHoField, 1, 0);
        grid.add(new Label("Họ tên:"), 0, 1);
        grid.add(hoTenField, 1, 1);
        grid.add(new Label("Ngày sinh:"), 0, 2);
        grid.add(ngaySinhField, 1, 2);
        grid.add(new Label("Giới tính:"), 0, 3);
        grid.add(gioiTinhChoice, 1, 3);
        grid.add(new Label("Dân tộc:"), 0, 4);
        grid.add(danTocField, 1, 4);
        grid.add(new Label("CCCD:"), 0, 5);
        grid.add(cccdField, 1, 5);
        grid.add(new Label("Nghề nghiệp:"), 0, 6);
        grid.add(ngheNghiepField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    LocalDate ngaySinh = LocalDate.parse(ngaySinhField.getText().trim());
                    return new NhanKhau(0,
                            maHoField.getText().trim(),
                            hoTenField.getText().trim(),
                            ngaySinh,
                            gioiTinhChoice.getValue(),  // Lấy giá trị giới tính từ ChoiceBox
                            danTocField.getText().trim(),
                            cccdField.getText().trim(),
                            ngheNghiepField.getText().trim());
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Ngày sinh phải đúng định dạng yyyy-MM-dd");
                    return null;
                }
            }
            return null;
        });

        Optional<NhanKhau> result = dialog.showAndWait();

        result.ifPresent(nk -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO nhankhau (hoten, ngaysinh, gioitinh, dantoc, cccd, nghenghiep) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, nk.getHoTen());
                ps.setDate(2, Date.valueOf(nk.getNgaySinh()));
                ps.setString(3, nk.getGioiTinh());
                ps.setString(4, nk.getDanToc());
                ps.setString(5, nk.getCccd());
                ps.setString(6, nk.getNgheNghiep());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int newId = rs.getInt(1);

                    // Chèn thêm vào bảng liên kết thanhvien_hokhau
                    String sql2 = "INSERT INTO thanhvien_hokhau (hokhau_id, nhankhau_id, ngaythem, quanhevoichuho) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps2 = conn.prepareStatement(sql2);
                    ps2.setString(1, nk.getMaHo());
                    ps2.setInt(2, newId);
                    ps2.setDate(3, new Date(System.currentTimeMillis()));
                    ps2.setString(4, "Thành viên");
                    ps2.executeUpdate();

                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm nhân khẩu mới!");
                    loadData();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi DB", "Không thể thêm nhân khẩu mới: " + e.getMessage());
            }
        });
    }


//router
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
    private Label lblNopPhi;

    @FXML
    public void handleNopPhiClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/NopPhi.fxml"));
            Parent canHoPage = loader.load();

            Stage stage = (Stage) lblNopPhi.getScene().getWindow();
            stage.setScene(new Scene(canHoPage));
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
