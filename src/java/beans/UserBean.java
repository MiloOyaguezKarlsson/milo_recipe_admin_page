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
import resources.UserResource;
import utilities.BCrypt;
import utilities.User;

@Named
@RequestScoped
public class UserBean {
    private List<User> users = new ArrayList<>();
    private String message;

    public UserBean() {
        //när bönan skapas hämtas data direkt
        users = UserResource.getUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String deleteUser(String username){
        boolean success = UserResource.deleteUser(username);
        
        if(success){
            message = username + " was deleted successfully";
        } else {
            message = "Delete failed";
        }
        
        //dirigera om till samma sida för att ladda om
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }
    
    public String setPassword(String password, String username){
        //notera att här kollar jag inte lösenordet mot något regex mönster 
        //eftersom detta är på adminsidan så tycker jag inte att det är 
        //nödvändigt och att admin ska ha full kontroll över sånt
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        
        boolean success = UserResource.setPassword(hash, username);
        
        if(success){
            message = "Password for " + username + " has been changed successfully";
        } else {
            message = "Password change failed";
        }
        
        //dirigera om till samma sida för att ladda om
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }
    
    public String promote(String username){
        boolean success = UserResource.promote(username);
        
        if(success){
            message = username + " was successfully promoted";
        } else {
            message = "Promotion failed";
        }
        
        //dirigera om till samma sida för att ladda om
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }
    
}
