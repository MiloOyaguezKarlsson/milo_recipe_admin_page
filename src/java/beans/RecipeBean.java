/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import resources.RecipeResource;
import utilities.Recipe;

@Named
@RequestScoped
public class RecipeBean {
    private List<Recipe> recipes = new ArrayList<>();
    private String message;

    public RecipeBean() {
        //när bönan skapas hämtas data direkt
        recipes = RecipeResource.getRecipes();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public String getMessage() {
        return message;
    }
    
    public String deleteRecipe(int id){
        System.out.println(id);
        boolean success = RecipeResource.deleteRecipe(id);
        
        if(success){
            message = "Recipe with id: " + id + " Was deleted successfully";
        } else {
            message = "Delete failed";
        }
        
        // dirigera till samma sida igen för att uppdatera
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }
    
    
    
}
