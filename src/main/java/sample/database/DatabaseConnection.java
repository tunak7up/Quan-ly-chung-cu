package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/chungcu";
            String user = "root";
            String password = "123456";

            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Kết nối thất bại: " + e.getMessage());
        }
        return conn;
    }
    public static void main(String[] args) {
        Connection test = getConnection();
        if (test != null) {
            System.out.println("Kết nối MySQL thành công.");
        } else {
            System.out.println("Kết nối thất bại.");
        }
    }

}
