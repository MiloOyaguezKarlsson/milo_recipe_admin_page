/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.BCrypt;
import utilities.ConnectionFactory;
import utilities.User;

// klass som hanterar databasdelen av användare och inlogging
public class UserResource {

    public static boolean validateLogin(String username, String password) {
        try {
            // koppla till databasen
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            //hämta användare med det användarnamnet
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                //användaren finns, kolla om lösen och rättigheter stämmer
                boolean confirmed = BCrypt.checkpw(password, result.getString("password"));
                // om lösenordet stämmer och användaren har admin-rättigheter
                if (confirmed && result.getInt("rights") == 2) {
                    connection.close();
                    return true;
                } else {
                    //lösenordet är fel eller användaren är inte admin
                    connection.close();
                    return false;
                }
            } else {
                //användaren finns inte
                connection.close();
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users");
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                users.add(new User(result.getString("username"), result.getString("password")));
            }

            connection.close();
            return users;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteUser(String username) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM users WHERE username = ?");
            stmt.setString(1, username);
            stmt.executeUpdate();

            connection.close();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean setPassword(String password, String username) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE users SET password = ? WHERE username = ?");
            stmt.setString(1, password);
            stmt.setString(2, username);
            stmt.executeUpdate();

            connection.close();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean promote(String username) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE users SET rights = ? WHERE username = ?");
            stmt.setInt(1, 2); //rights = 2 är admin-rättigheter medans 1 är vanliga användare
            stmt.setString(2, username);
            stmt.executeUpdate();
            
            connection.close();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
