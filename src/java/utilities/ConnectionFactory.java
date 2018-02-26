package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//klass för koppling till databasen
public class ConnectionFactory {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // ändra dessa beroende på om det är lokalteller på servern
        String url = "jdbc:mysql://localhost//milo_recipe_db";
        String user = "milo_te4";
        String pass = "milote4milote4";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }
}
