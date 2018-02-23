package utilities;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

// klass för att hämta sessionen, kanske inte nödvändig men om jag vill
// använda det på fler ställen i framtiden blir det enklare
public class SessionUtils {
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }
}
