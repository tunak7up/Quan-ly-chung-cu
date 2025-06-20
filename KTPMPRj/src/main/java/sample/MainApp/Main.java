package main.java.sample.MainApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main/java/sample/Views/LoginWindow.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setTitle("Đăng nhập hệ thống");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.centerOnScreen();
            primaryStage.show();

            System.out.println("Ứng dụng khởi chạy thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}