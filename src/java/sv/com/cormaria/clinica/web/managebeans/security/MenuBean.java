package sv.com.cormaria.clinica.web.managebeans.security;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.richfaces.PanelMenuMode;
import org.richfaces.component.UIMenuItem;
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

    private UIPanelMenu menu = (UIPanelMenu) FacesContext.getCurrentInstance().getApplication().createComponent(FacesContext.getCurrentInstance(), 
                UIPanelMenu.COMPONENT_TYPE, "org.richfaces.PanelMenuRenderer");
    
    @EJB
    CatMenuSessionFacadeLocal menuSessionFacade;
    private HashSet<CatMenu> menus = new HashSet<CatMenu>();

    @ManagedProperty(value="#{clinicaSessionBean.usuario.aliUsuario}")
    private String codUsuario;

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        System.out.println("Codigo usuario: "+codUsuario);
        this.codUsuario = codUsuario;
    }
        
    public HashSet<CatMenu> getMenus() {
            return menus;
    }

    public void setMenus(HashSet<CatMenu> menus) {
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
    
	private void generateSubOptions(CatMenu mainMenuData, UIComponent mainMenu){
                System.out.println("Creando submenus...");
		for (CatMenu menuData : mainMenuData.getOptions()) {
			System.out.println("Populating menu: "+menuData.getNomOpcMenu());
			if (menuData.getOptions().isEmpty()){
				if (menuData.getNomOpcMenu().equals("-")){
					UIMenuSeparator separator = (UIMenuSeparator) FacesContext.getCurrentInstance().getApplication().createComponent(FacesContext.getCurrentInstance(), 
                UIMenuSeparator.COMPONENT_TYPE, "org.richfaces.MenuSeparatorRenderer");
					separator.setId("separator_"+menuData.getCodMenu());
					mainMenu.getChildren().add(separator);
				}else{
					UIPanelMenuItem item = (UIPanelMenuItem) FacesContext.getCurrentInstance().getApplication().createComponent(FacesContext.getCurrentInstance(), 
                UIPanelMenuItem.COMPONENT_TYPE, "org.richfaces.PanelMenuItemRenderer");
					item.setId("menuItem_"+menuData.getCodMenu());
                                        System.out.println("menuData.getUrlIniMenu(): "+menuData.getUrlIniMenu());
                                        //FacesContext.getCurrentInstance().getApplication().
					item.setActionExpression(FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), menuData.getUrlIniMenu()+"?faces-redirect=true", String.class, new Class[]{}));
                                        item.setSelectable(true);
                                        item.setValue(menuData.getUrlIniMenu()+"?faces-redirect=true");
					item.setLabel(menuData.getNomOpcMenu());
					mainMenu.getChildren().add(item);
				}
			}
			if (!menuData.getOptions().isEmpty()){
				if (menuData.getNomOpcMenu().equals("-")){
					UIMenuSeparator separator = (UIMenuSeparator) FacesContext.getCurrentInstance().getApplication().createComponent(FacesContext.getCurrentInstance(), 
                UIMenuSeparator.COMPONENT_TYPE, "org.richfaces.MenuSeparatorRenderer");
					separator.setId("separator_"+menuData.getCodMenu());
					mainMenu.getChildren().add(separator);
				}else{
					UIPanelMenuGroup group = (UIPanelMenuGroup) FacesContext.getCurrentInstance().getApplication().createComponent(FacesContext.getCurrentInstance(), UIPanelMenuGroup.COMPONENT_TYPE, "org.richfaces.PanelMenuGroupRenderer");
					mainMenu.getChildren().add(group);
					group.setValue(false);
                                        group.setLabel(menuData.getNomOpcMenu());
					group.setId("menuGroup_"+menuData.getCodMenu());
					generateSubOptions(menuData, group);
				}
			}
		}
	}
	
	public UIPanelMenu getMenu() {
                if (menu == null){
                    menu = (UIPanelMenu) FacesContext.getCurrentInstance().getApplication().createComponent(FacesContext.getCurrentInstance(), 
                UIPanelMenu.COMPONENT_TYPE, "org.richfaces.PanelMenuRenderer");
                }
                
		if (menu.getChildCount()<=0){
			if (this.getSessionBean().getUsuario()!=null){
				loadMenuOptions(this.getSessionBean().getUsuario().getAliUsuario());
			}
		}
		return menu;
	}

	public void setMenu(UIPanelMenu menu) {
		this.menu = menu;
	}

	public void loadMenuOptions(String codigousuario){
		try{
                    FacesContext context = FacesContext.getCurrentInstance(); 
                    Application application = context.getApplication(); 
                    //menus = menuSessionFacade.findActivePublicMenuByUserId(codigousuario);
                    System.out.println("Creando menus... "+codigousuario);
                    ClinicaSessionBean sessionBean = this.getSessionBean();
                    if (sessionBean.getUsuario()!=null && sessionBean.getUsuario().getSelectedModule()!=null){
                        menus = sessionBean.getUsuario().getSelectedModule().getOptions();
                        try{
                            if (!menus.isEmpty()){
                                System.out.println("Populating menu");
                                for (CatMenu menuData: menus) {
                                    System.out.println("Populating menu:"+menuData.getNomOpcMenu());
                                    UIPanelMenuGroup ddMenu = (UIPanelMenuGroup) application.createComponent(context, UIPanelMenuGroup.COMPONENT_TYPE, "org.richfaces.PanelMenuGroupRenderer");
                                    ddMenu.setExpanded(false);
                                    ddMenu.setValue(false);
                                    ddMenu.setMode(PanelMenuMode.ajax);
                                    ddMenu.setLabel(menuData.getNomOpcMenu());
                                    ddMenu.setId("ddMenu_"+menuData.getCodMenu());
                                    menu.getChildren().add(ddMenu);
                                    generateSubOptions(menuData, ddMenu);
                                }
                            }
                        }catch(Exception ex){
                                ex.printStackTrace();
                        }
                    }
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.toString());
		}
	}
	
	public void loadMenuOptions(TblUsuarios usuario){
          try{
            if (usuario!=null && usuario.getSelectedModule()!=null){
                menus = usuario.getSelectedModule().getOptions();
                try{
                        if (!menus.isEmpty()){
                            System.out.println("Populating menu");
                            for (CatMenu menuData: menus) {
                                System.out.println("Populating menu:"+menuData.getNomOpcMenu());
                                UIPanelMenuGroup ddMenu = new UIPanelMenuGroup();
                                ddMenu.setValue(menuData.getNomOpcMenu());
                                ddMenu.setId("ddMenu_"+menuData.getCodMenu());
                                menu.getChildren().add(ddMenu);
                                generateSubOptions(menuData, ddMenu);
                            }
                        }
                }catch(Exception ex){
                        ex.printStackTrace();
                }
            }
        }catch(Exception ex){
                ex.printStackTrace();
                this.addError(ex.getMessage(), ex.toString());
        }
      }    
        
        public void resetMenu(ActionEvent ae){
            this.menu = (UIPanelMenu) FacesContext.getCurrentInstance().getApplication().createComponent(FacesContext.getCurrentInstance(), 
                UIPanelMenu.COMPONENT_TYPE, "org.richfaces.PanelMenuRenderer");
            this.loadMenuOptions(codUsuario);
        }
}