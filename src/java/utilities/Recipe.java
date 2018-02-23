/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.ArrayList;
import java.util.List;

// klass för ett recept
public class Recipe {
    private int id, portions;
    private String name, user, description, instructions, picture, date;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    public Recipe(int id, int portions, String name, String user, 
            String description, String instructions, String picture, String date,
            List<Ingredient> ingredients, List<String> tags) {
        this.id = id;
        this.portions = portions;
        this.name = name;
        this.user = user;
        this.description = description;
        this.instructions = instructions;
        this.picture = picture;
        this.date = date;
        this.ingredients = ingredients;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    
    
    
}
