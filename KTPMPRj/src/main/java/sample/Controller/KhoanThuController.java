package main.java.sample.Controller;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.stage.Stage;
import main.java.sample.Model.KhoanThu;

import javafx.geometry.Insets;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class KhoanThuController {

    @FXML private TableView<KhoanThu> tableKhoanThu;
    @FXML private TableColumn<KhoanThu, Integer> colId;
    @FXML private TableColumn<KhoanThu, LocalDate> colNgayTao;
    @FXML private TableColumn<KhoanThu, LocalDate> colThoiHan;
    @FXML private TableColumn<KhoanThu, String> colTenKhoanThu;
    @FXML private TableColumn<KhoanThu, Boolean> colBatBuoc;
    @FXML private TableColumn<KhoanThu, String> colGhiChu;
    @FXML private TableColumn<KhoanThu, Void> colAction;

    @FXML private TextField txtSearch;

    private ObservableList<KhoanThu> khoanThuList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNgayTao.setCellValueFactory(new PropertyValueFactory<>("ngayTao"));
        colThoiHan.setCellValueFactory(new PropertyValueFactory<>("thoiHan"));
        colTenKhoanThu.setCellValueFactory(new PropertyValueFactory<>("tenKhoanThu"));
        colBatBuoc.setCellValueFactory(new PropertyValueFactory<>("batBuoc"));
        colGhiChu.setCellValueFactory(new PropertyValueFactory<>("ghiChu"));

        // Format ngày tháng hiển thị
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colNgayTao.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        });
        colThoiHan.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        });

        // Format cột bắt buộc là checkbox
        colBatBuoc.setCellFactory(column -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(item);
                    checkBox.setDisable(true);
                    setGraphic(checkBox);
                }
            }
        });

        addButtonToTable();
        loadData();
    }

    private void loadData() {
        khoanThuList.clear();
        String sql = "SELECT id, ngaytao, thoihan, tenkhoanthu, batbuoc, ghichu FROM khoanthu";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                KhoanThu kt = new KhoanThu();
                kt.setId(rs.getInt("id"));
                Date nt = rs.getDate("ngaytao");
                if (nt != null) kt.setNgayTao(nt.toLocalDate());
                Date th = rs.getDate("thoihan");
                if (th != null) kt.setThoiHan(th.toLocalDate());
                kt.setTenKhoanThu(rs.getString("tenkhoanthu"));
                kt.setBatBuoc(rs.getBoolean("batbuoc"));
                kt.setGhiChu(rs.getString("ghichu"));

                khoanThuList.add(kt);
            }

            tableKhoanThu.setItems(khoanThuList);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi truy vấn", "Không thể tải dữ liệu khoản thu.");
        }
    }

    @FXML
    private void onSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            tableKhoanThu.setItems(khoanThuList);
            return;
        }

        ObservableList<KhoanThu> filtered = FXCollections.observableArrayList();
        for (KhoanThu kt : khoanThuList) {
            if (kt.getTenKhoanThu() != null && kt.getTenKhoanThu().toLowerCase().contains(keyword)) {
                filtered.add(kt);
            }
        }
        tableKhoanThu.setItems(filtered);
    }

    @FXML
    private void onRefresh() {
        txtSearch.clear();
        loadData();
    }

    @FXML
    private void onAdd() {
        Dialog<KhoanThu> dialog = new Dialog<>();
        dialog.setTitle("Thêm mới khoản thu");
        dialog.setHeaderText("Nhập thông tin khoản thu");

        ButtonType addBtn = new ButtonType("Thêm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField tenField = new TextField();
        tenField.setPromptText("Tên khoản thu");
        DatePicker ngayTaoPicker = new DatePicker();
        DatePicker thoiHanPicker = new DatePicker();
        CheckBox batBuocCheck = new CheckBox("Bắt buộc");
        TextField ghiChuField = new TextField();
        ghiChuField.setPromptText("Ghi chú");

        grid.add(new Label("Tên khoản thu:"), 0, 0);
        grid.add(tenField, 1, 0);
        grid.add(new Label("Ngày tạo:"), 0, 1);
        grid.add(ngayTaoPicker, 1, 1);
        grid.add(new Label("Thời hạn:"), 0, 2);
        grid.add(thoiHanPicker, 1, 2);
        grid.add(batBuocCheck, 1, 3);
        grid.add(new Label("Ghi chú:"), 0, 4);
        grid.add(ghiChuField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == addBtn) {
                KhoanThu kt = new KhoanThu();
                kt.setTenKhoanThu(tenField.getText().trim());
                kt.setNgayTao(ngayTaoPicker.getValue());
                kt.setThoiHan(thoiHanPicker.getValue());
                kt.setBatBuoc(batBuocCheck.isSelected());
                kt.setGhiChu(ghiChuField.getText().trim());
                return kt;
            }
            return null;
        });

        Optional<KhoanThu> result = dialog.showAndWait();

        result.ifPresent(kt -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "INSERT INTO khoanthu (tenkhoanthu, ngaytao, thoihan, batbuoc, ghichu) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, kt.getTenKhoanThu());
                ps.setDate(2, kt.getNgayTao() == null ? null : Date.valueOf(kt.getNgayTao()));
                ps.setDate(3, kt.getThoiHan() == null ? null : Date.valueOf(kt.getThoiHan()));
                ps.setBoolean(4, kt.isBatBuoc());
                ps.setString(5, kt.getGhiChu());
                ps.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm khoản thu mới");
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm khoản thu mới: " + e.getMessage());
            }
        });
    }

    private void addButtonToTable() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("Sửa");
            private final Button btnDelete = new Button("Xóa");
            private final HBox pane = new HBox(10, btnEdit, btnDelete);

            {
                btnEdit.setOnAction(event -> {
                    KhoanThu kt = getTableView().getItems().get(getIndex());
                    onEdit(kt);
                });
                btnDelete.setOnAction(event -> {
                    KhoanThu kt = getTableView().getItems().get(getIndex());
                    onDelete(kt);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private void onEdit(KhoanThu kt) {
        Dialog<KhoanThu> dialog = new Dialog<>();
        dialog.setTitle("Sửa khoản thu");
        dialog.setHeaderText("Cập nhật thông tin khoản thu");

        ButtonType saveBtn = new ButtonType("Lưu", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField tenField = new TextField(kt.getTenKhoanThu());
        DatePicker ngayTaoPicker = new DatePicker(kt.getNgayTao());
        DatePicker thoiHanPicker = new DatePicker(kt.getThoiHan());
        CheckBox batBuocCheck = new CheckBox("Bắt buộc");
        batBuocCheck.setSelected(kt.isBatBuoc());
        TextField ghiChuField = new TextField(kt.getGhiChu());

        grid.add(new Label("Tên khoản thu:"), 0, 0);
        grid.add(tenField, 1, 0);
        grid.add(new Label("Ngày tạo:"), 0, 1);
        grid.add(ngayTaoPicker, 1, 1);
        grid.add(new Label("Thời hạn:"), 0, 2);
        grid.add(thoiHanPicker, 1, 2);
        grid.add(batBuocCheck, 1, 3);
        grid.add(new Label("Ghi chú:"), 0, 4);
        grid.add(ghiChuField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
            if (button == saveBtn) {
                KhoanThu updated = new KhoanThu();
                updated.setId(kt.getId());
                updated.setTenKhoanThu(tenField.getText().trim());
                updated.setNgayTao(ngayTaoPicker.getValue());
                updated.setThoiHan(thoiHanPicker.getValue());
                updated.setBatBuoc(batBuocCheck.isSelected());
                updated.setGhiChu(ghiChuField.getText().trim());
                return updated;
            }
            return null;
        });

        Optional<KhoanThu> result = dialog.showAndWait();

        result.ifPresent(updatedKt -> {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "UPDATE khoanthu SET tenkhoanthu=?, ngaytao=?, thoihan=?, batbuoc=?, ghichu=? WHERE id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, updatedKt.getTenKhoanThu());
                ps.setDate(2, updatedKt.getNgayTao() == null ? null : Date.valueOf(updatedKt.getNgayTao()));
                ps.setDate(3, updatedKt.getThoiHan() == null ? null : Date.valueOf(updatedKt.getThoiHan()));
                ps.setBoolean(4, updatedKt.isBatBuoc());
                ps.setString(5, updatedKt.getGhiChu());
                ps.setInt(6, updatedKt.getId());

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Cập nhật khoản thu thành công");
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Cập nhật thất bại");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Lỗi DB", e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void onDelete(KhoanThu kt) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xóa khoản thu");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa khoản thu: " + kt.getTenKhoanThu() + "?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "DELETE FROM khoanthu WHERE id=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, kt.getId());
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã xóa khoản thu");
                        loadData();
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Lỗi", "Xóa thất bại");
                    }
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi DB", e.getMessage());
                    e.printStackTrace();
                }
            }
        });
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
    private Label lblThongKe;

    @FXML
    public void handleThongKeClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/ThongKe2.fxml"));
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
