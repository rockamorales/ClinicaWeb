package sv.com.cormaria.clinica.web.managebeans.security;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.richfaces.component.UIMenuSeparator;
import org.richfaces.component.UIPanelMenu;
import org.richfaces.component.UIPanelMenuGroup;
import org.richfaces.component.UIPanelMenuItem;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.sessions.ClinicaSessionBean;
import sv.com.cormaria.servicios.entidades.security.CatMenu;
import sv.com.cormaria.servicios.entidades.security.TblUsuarios;
import sv.com.cormaria.servicios.facades.security.CatMenuSessionFacadeLocal;
import sv.com.cormaria.servicios.facades.security.TblUsuariosSessionFacadeLocal;

/**
 * @author Administrador
 *
 */
@ManagedBean(name="menuBean", eager=false)
@SessionScoped
public class MenuBean extends PageBase{
	
    @EJB
    TblUsuariosSessionFacadeLocal usuarioSession;

    @EJB
    CatMenuSessionFacadeLocal menuSessionFacade;
    private Set<CatMenu> menus = new HashSet<CatMenu>();

    @ManagedProperty(value="#{request.userPrincipal.name}")
    private String codUsuario;

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        System.out.println("Codigo usuario: "+codUsuario);
        if (codUsuario!=null){
            try{
                this.getSessionBean().setUsuario(usuarioSession.findByCodigoUsuarioWithMenu(codUsuario));
            }catch(Exception ex){
                ex.printStackTrace();
                this.addError(ex.getMessage(), ex.getMessage());
            }
            //this.loadMenuOptions(codUsuario);
        }
        this.codUsuario = codUsuario;
    }
        
    public Set<CatMenu> getMenus() {
            return menus;
    }

    public void setMenus(Set<CatMenu> menus) {
            this.menus = menus;
    }
    
    public ArrayList<CatMenu> getModulos(){
        if (this.getSessionBean().getUsuario()==null){
            String codUsuario1 = (String)this.getBean("#{request.userPrincipal.name}", String.class);
            System.out.println("codUsuario: "+codUsuario1);
            if (codUsuario1!=null && !codUsuario1.trim().equals("")){
                try{
                    TblUsuarios usuario = usuarioSession.findByCodigoUsuarioWithMenu(codUsuario1);
                    this.getSessionBean().setUsuario(usuario);
                    usuario.setUltFecIniSesion(new java.util.Date());
                    usuarioSession.updateAll(usuario);
                }catch(Exception ex){
                    ex.printStackTrace();
                    this.addError(ex.getMessage(), ex.getMessage());
                }
            }
        }
        if (this.getSessionBean().getUsuario()!=null){
            return this.getSessionBean().getUsuario().getModulos();
        }
        return new ArrayList<CatMenu>();
    }
}