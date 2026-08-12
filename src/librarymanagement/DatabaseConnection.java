package librarymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static final String URL="jdbc:postgresql://localhost:5432/db_library_management";
    public static final String USER="postgres";
    public static final String PASSWORD="Wikipedia0411@";
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối đến CSDL" + e.getMessage());
        }
        return connection;
    }
}
