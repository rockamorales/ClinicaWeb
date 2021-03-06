package sv.com.cormaria.clinica.web.managebeans.security;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.sessions.ClinicaSessionBean;
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.entidades.security.CatRolesUsuario;
import sv.com.cormaria.servicios.entidades.security.TblUsuarios;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.security.TblUsuariosSessionFacadeLocal;



@ManagedBean(name="frmLogin")
@RequestScoped
public class FrmLogin extends PageBase {
    @EJB
    TblUsuariosSessionFacadeLocal usuarioSession;
    @EJB
    TblProductoFacadeLocal productoSession;
    private String usuario;
    private String contrasena;
    public String getUsuario() {
            return usuario;
    }
    public void setUsuario(String usuario) {
            this.usuario = usuario;
    }
    public String getContrasena() {
            return contrasena;
    }
    public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
    }
	
    public String autenticar(){
        try{
            System.out.println("Buscando el usuario");
            TblUsuarios usuario1 = usuarioSession.findByCodigoUsuarioWithMenu(this.usuario);
            //MenuBean menu = (MenuBean) this.getBean("#{menuBean}", MenuBean.class);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
            request.login(this.usuario, this.contrasena);
            if (usuario1!=null){
                ClinicaSessionBean sessionBean = this.getSessionBean();
                sessionBean.setUsuario(usuario1);
                usuario1.setUltFecIniSesion(new java.util.Date());
                usuarioSession.updateAll(usuario1);
                boolean hasRole = false;
                for (CatRolesUsuario roleUsuario : usuario1.getRolesusuario()) {
                    if (roleUsuario.getCatRole().getNomRol().equals("farmacia")){
                        hasRole = true;
                    }
                }
                if (hasRole){
                    productoSession.generarAlertas();
                }
                //menu.loadMenuOptions(usuario);
                return "inicio?faces-redirect=true";
            }else{
                this.addError("El usuario no existe o esta desactivado", "El usuario no existe o esta desactivado");
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError("Favor Ingrese su contraseña","Favor Ingrese su contraseña");
        }
        return null;
    }
        
   public String logout() {
        String result="/login?faces-redirect=true";
        try {
            TblUsuarios usuario = this.getSessionBean().getUsuario();
            usuario.setUltFecFinSesion(new java.util.Date());
            usuarioSession.updateAll(usuario);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
            HttpSession session = (HttpSession)context.getExternalContext().getSession(false);
            addInfo("Se ha finalizado la Sesion", "Se ha finalizado la Sesion");
            session.invalidate();
            request.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}