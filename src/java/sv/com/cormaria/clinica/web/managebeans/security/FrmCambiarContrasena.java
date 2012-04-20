package sv.com.cormaria.clinica.web.managebeans.security;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.facades.security.TblUsuariosSessionFacadeLocal;


@ManagedBean(name="frmCambiarContrasena")
@RequestScoped
public class FrmCambiarContrasena extends PageBase{
	@EJB
	TblUsuariosSessionFacadeLocal usuarioLocal;
	
	private String oldPassword;
	private String newPassword;
	private String confNewPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getConfNewPassword() {
		return confNewPassword;
	}
	
	public void setConfNewPassword(String confNewPassword) {
		this.confNewPassword = confNewPassword;
	}
	
	public String cambiarContrasena(){
		try{
			usuarioLocal.cambiarContrasena(this.getSessionBean().getUsuario().getAliUsuario(),this.getOldPassword(), this.getNewPassword(), this.getConfNewPassword());
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			session.invalidate();
			return "login?faces-redirect=true";
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.toString());
		}
		return null;
	}
}
