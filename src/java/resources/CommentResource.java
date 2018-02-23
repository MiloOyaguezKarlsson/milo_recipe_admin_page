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
import utilities.Comment;
import utilities.ConnectionFactory;

public class CommentResource {

    public static List<Comment> getComments() {
        List<Comment> comments = new ArrayList<>();
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM comments");
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                comments.add(new Comment(
                        result.getString("comment"),
                        result.getString("user"),
                        result.getInt("recipe"),
                        result.getInt("id"),
                        result.getDate("date").toString()));
            }
            connection.close();
            return comments;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteComment(int id) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM comments WHERE id = ?");
            stmt.setInt(1, id);
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
