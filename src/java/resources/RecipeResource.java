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
import utilities.ConnectionFactory;
import utilities.Recipe;
import utilities.Ingredient;

public class RecipeResource {

    public static List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.
                    prepareStatement("SELECT id, name, username AS user, date, "
                            + "picture, description, instructions, portions, "
                            + "COUNT(recipe) "
                            + "AS upvotes FROM (recipes_join_upvotes) GROUP BY id");
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                List<Ingredient> ingredients = new ArrayList<>();
                ingredients = getIngredients(result.getInt("id"));
                List<String> tags = new ArrayList<>();
                tags = getTags(result.getInt("id"));

                recipes.add(new Recipe(
                        result.getInt("id"),
                        result.getInt("portions"),
                        result.getString("name"),
                        result.getString("user"),
                        result.getString("description"),
                        result.getString("instructions"),
                        result.getString("picture"),
                        result.getDate("date").toString(),
                        ingredients,
                        tags
                ));
            }
            return recipes;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Recipe getRecipe(int recipeID){
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.
                    prepareStatement("SELECT id, name, recipes.user, date, "
                            + "description, instructions, picture,  portions, "
                            + "COUNT(id) AS upvotes FROM recipes,upvotes "
                            + "WHERE recipes.id = upvotes.recipe AND recipes.id = ?");
            stmt.setInt(1, recipeID);
            ResultSet result = stmt.executeQuery();
            
            Recipe recipe = new Recipe(
                        result.getInt("id"),
                        result.getInt("portions"),
                        result.getString("name"),
                        result.getString("username"),
                        result.getString("description"),
                        result.getString("instructions"),
                        result.getString("picture"),
                        result.getDate("date").toString(),
                        getIngredients(recipeID),
                        getTags(recipeID));
            return recipe;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    private static List<Ingredient> getIngredients(int recipeID) {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.
                    prepareStatement("SELECT grocery, amount, measurement FROM "
                            + "ingredients WHERE recipe = ?");
            stmt.setInt(1, recipeID);
            ResultSet result = stmt.executeQuery();
            
            //skapa ingredienser
            while(result.next()){
                ingredients.add(new Ingredient(result.getString("grocery"),
                        result.getString("measurement"),
                        result.getDouble("amount")));
            }
            //returna listan med ingredienser
            return ingredients;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getTags(int recipeID) {
        List<String> tags = new ArrayList<>();
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.
                    prepareStatement("SELECT name FROM tags WHERE recipe = ?");
            stmt.setInt(1, recipeID);
            ResultSet result = stmt.executeQuery();
            
            while(result.next()){
                tags.add(result.getString("name"));
            }
            return tags;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteRecipe(int recipeID) {
        try {
            Connection connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM recipes WHERE id = ?");
            stmt.setInt(1, recipeID);
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
