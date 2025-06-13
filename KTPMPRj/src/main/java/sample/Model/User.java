package main.java.sample.Model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty username;
    private StringProperty password;
    private StringProperty vaitro;

    public User() {
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.vaitro = new SimpleStringProperty();
    }

    public String getUsername() { return username.get(); }
    public void setUsername(String username) { this.username.set(username); }
    public StringProperty usernameProperty() { return username; }

    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }
    public StringProperty passwordProperty() { return password; }

    public String getVaitro() { return vaitro.get(); }
    public void setVaitro(String vaitro) { this.vaitro.set(vaitro); }
    public StringProperty vaitroProperty() { return vaitro; }
}

