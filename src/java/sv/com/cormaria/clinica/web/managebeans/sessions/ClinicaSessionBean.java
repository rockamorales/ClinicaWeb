package sv.com.cormaria.clinica.web.managebeans.sessions;



import java.io.Serializable;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sv.com.cormaria.servicios.entidades.security.TblUsuarios;

@ManagedBean(name="clinicaSessionBean")
@SessionScoped
public class ClinicaSessionBean implements Serializable{
    private TblUsuarios usuario;
    private String currentHelpUrl;
    public String getCurrentHelpUrl() {
            return currentHelpUrl;
    }

    public void setCurrentHelpUrl(String currentHelpUrl) {
            this.currentHelpUrl = currentHelpUrl;
    }

    public TblUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(TblUsuarios usuario) {
        
        this.usuario = usuario;
    }
        
    public boolean isHasMessages(){
    	return FacesContext.getCurrentInstance().getMessages().hasNext();
    }	

    public boolean isHasErrorsMessages(){
    	Iterator<FacesMessage> itMessages = FacesContext.getCurrentInstance().getMessages();
    	FacesMessage facesMessage = null;
        System.out.println("Mensaje de error?");
    	while(itMessages.hasNext()){
                System.out.println("Severety: "+facesMessage.getSeverity());
    		facesMessage = itMessages.next();
    		if ( facesMessage.getSeverity().equals(FacesMessage.SEVERITY_ERROR) || facesMessage.getSeverity().equals(FacesMessage.SEVERITY_FATAL)){
    			return true;
    		}
    	}
        return false;
    }
    
    public String logout(){
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.invalidate();
            return "/inicio?faces-redirect=false";
    }

    public String loginAnotherUser(){
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.invalidate();
            return "/login?faces-redirect=true";
    }
}
