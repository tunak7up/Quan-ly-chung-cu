package main.java.sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main4 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Tạo dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Sign Up");

        // Nút Login và Cancel
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Tạo GridPane để chứa các control
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Tạo TextField cho username và password
        TextField userName = new TextField();
        userName.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        // Thêm các control vào grid
        grid.add(new Label("Username:"), 0, 0);
        grid.add(userName, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Lấy nút login để enable/disable dựa trên input username
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Lắng nghe thay đổi text ở username để enable nút login khi có text
        userName.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        // Xử lý kết quả dialog khi bấm Login
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(userName.getText(), password.getText());
            }
            return null;
        });

        // Hiển thị dialog và nhận kết quả
        dialog.showAndWait().ifPresent(result -> {
            System.out.println("Username=" + result.getKey() + ", Password=" + result.getValue());
        });
    }
}
