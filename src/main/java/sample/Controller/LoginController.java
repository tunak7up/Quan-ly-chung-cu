package main.java.sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public void Submit() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try (Connection conn = database.DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Chuyển sang màn hình chính
                String role = rs.getString("vaitro");
                FXMLLoader loader;
                if("to_truong".equals(role)) {
                     loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HomePage.fxml"));
                } else if ("ke_toan".equals(role)) {
                     loader = new FXMLLoader(getClass().getResource("/main/java/sample/Views/HomePage2.fxml"));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Tài khoản không có vai trò phù hợp.");
                    return;
                }

                Parent root = loader.load();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Trang chính");
                stage.show();

            } else {
                showAlert(Alert.AlertType.ERROR, "Sai tài khoản hoặc mật khẩu.");
            }

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
