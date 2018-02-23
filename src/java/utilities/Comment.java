/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;


public class Comment {
    private String comment;
    private String user;
    private int recipeID;
    private int id;
    private String date;

    public Comment(String comment, String user, int recipeID, int id, String date) {
        this.comment = comment;
        this.user = user;
        this.recipeID = recipeID;
        this.id = id;
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public String getUser() {
        return user;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
