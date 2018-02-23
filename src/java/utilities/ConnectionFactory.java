package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//klass för koppling till databasen
public class ConnectionFactory {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost/recipe_db";
        String user = "root";
        String pass = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }
}
