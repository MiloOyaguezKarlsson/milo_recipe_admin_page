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
import resources.CommentResource;
import utilities.Comment;

@Named
@RequestScoped
public class CommentBean {

    private List<Comment> comments = new ArrayList<>();
    private String message;

    public CommentBean() {
        //när bönan skapas hämtas data direkt
        comments = CommentResource.getComments();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String deleteComment(int id){
        boolean success = CommentResource.deleteComment(id);
        
        if(success){
            message = "Comment with id: " + id + " was successfully deleted";
        } else {
            message = "Delete failed";
        }
        
        //diregera till samma sida igen för att uppdatera
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }
}
