package beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import resources.UserResource;
import utilities.SessionUtils;

// böna för att hantera inloggningen, använder sig av klasserna UserResource 
// för att validera inloggning, SessionUtils för att hantera sessioner på sidan
@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private String message;

    public String login() {
        boolean valid = UserResource.validateLogin(username, password);
        if (valid) {
            // ställa in användare som inloggad i sessionen
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("user", username);
            // returnera vilken sida som man ska skickas till
            message = "";
            return "admin";
        } else {
            message = "Incorrect username or password";
            // om fel inloggning skicka tillbaka till index (inloggnings sidan)
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true";
        }
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true";
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
