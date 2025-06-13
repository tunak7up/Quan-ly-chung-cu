package main.java.sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Man2 extends Application {
    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        // Scene 1
        Label label1 = new Label("Welcome to Scene 1");
        Button button1 = new Button("Go to Scene 2");
        button1.setOnAction(event -> window.setScene(scene2));
        VBox layout1 = new VBox(10);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 300, 200);

        // Scene 2
        Label label2 = new Label("You're now in Scene 2");
        Button button2 = new Button("Go back to Scene 1");
        button2.setOnAction(event -> window.setScene(scene1));
        StackPane layout2 = new StackPane();
        layout2.getChildren().addAll(label2, button2);
        scene2 = new Scene(layout2, 300, 200);

        window.setTitle("Scene Switcher");
        window.setScene(scene1);
        window.show();
    }
}
