module KTPMProject {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;
    requires java.sql;
    exports main.java.sample.Controller;

    opens main.java.sample.Controller to javafx.fxml;
    opens main.java.sample.Model to javafx.base, javafx.fxml;
    exports main.java.sample.MainApp;
    exports main.java.sample;
}