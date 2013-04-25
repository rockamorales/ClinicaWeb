package sv.com.cormaria.clinica.web.managebeans.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.UITree;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.security.CatMenu;
import sv.com.cormaria.servicios.entidades.security.CatRoles;
import sv.com.cormaria.servicios.entidades.security.CatRolesMenu;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;
import sv.com.cormaria.servicios.facades.security.CatMenuSessionFacadeLocal;
import sv.com.cormaria.servicios.facades.security.CatRolesMenuSessionFacadeLocal;
import sv.com.cormaria.servicios.facades.security.CatRolesSessionFacadeLocal;
import sv.com.cormaria.servicios.helpers.ValidationUtils;


@ManagedBean(name="frmRolesMenu")
@RequestScoped
public class FrmRolesMenu extends PageBase{
	@EJB
	transient CatMenuSessionFacadeLocal menuSession;
	
	@EJB
	transient CatRolesMenuSessionFacadeLocal rolesMenuSession;
	
	@EJB
	transient CatRolesSessionFacadeLocal roleSession;
	
	public List<CatMenu> menuList = new ArrayList<CatMenu>();
	public List<CatRoles> rolesList = new ArrayList<CatRoles>();
	public Long selectedMenuId;

	private List<CatMenu> getStructuredMenu(List<CatMenu> menuList){
		List<CatMenu> options = new ArrayList<CatMenu>();
		for (CatMenu menu : menuList){
                        System.out.println("Menu: "+menu.getNomOpcMenu());
			if (menu.getCodPadMenu() == null){
				options.add(menu);
			}else{
				CatMenu menupadre = findMenuByCod(menu.getCodPadMenu(), menuList);
				if (menupadre!=null){
					menupadre.getOptions().add(menu);
				}
			}
		}
		return options;
	}
	
	private CatMenu findMenuByCod(Long codigomenu, List<CatMenu> menuList){
		for (CatMenu menu : menuList) {
			if (menu.getCodMenu().equals(codigomenu)){
				return menu;
			}
		}
		return null;
	}
	public List<CatMenu> getMenuList() {
		if (menuList.isEmpty()){
			try {
				Rol role = (Rol) this.getBean("#{role}", Rol.class);
				System.out.println("Role: "+role.getCatRol().getCodRol()+" -- "+role.getCatRol().getNomRol());
				menuList = getStructuredMenu(menuSession.findAllPublicMenu(role.getCatRol().getCodRol()));
			} catch (ClinicaModelexception e) {
				e.printStackTrace();
			}
		}
		return menuList;
	}

	public void setMenuList(List<CatMenu> menuList) {
		this.menuList = menuList;
	}
	
	public void addRemoveOption(ActionEvent ae){
		try{
			UITree node = (UITree)ae.getComponent().getParent().getParent().getParent().getParent();
			CatMenu menu = (CatMenu)node.getRowData();
			Rol role = (Rol) this.getBean("#{role}", Rol.class);
			rolesMenuSession.addOrRemoveOption(role.getCatRol().getCodRol(), menu, menu.getInrole());
			this.setMenuList(new ArrayList<CatMenu>());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.getMessage());
		}
	}

	public Long getSelectedMenuId() {
		return selectedMenuId;
	}

	public void setSelectedMenuId(Long selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}

	public List<CatRoles> getRolesList() {
		if (rolesList.isEmpty()){
			try{
				rolesList = roleSession.findAllActiveRoles();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return rolesList;
	}

	public void setRolesList(List<CatRoles> rolesList) {
		this.rolesList = rolesList;
	}

    public boolean validar(){
        boolean isValid = true;
        Rol role = (Rol) this.getBean("#{role}", Rol.class);
        if (role.getCatRol().getNomRol() == null || role.getCatRol().getNomRol().trim().equals("")){
            this.addError("Por favor ingrese el nombre del nuevo rol", "Por favor ingrese el nombre del nuevo rol");
            isValid = false;
        }
        if (role.getCatRol().getDesRol() == null || role.getCatRol().getDesRol().trim().equals("")){
            this.addError("Por favor ingrese la descripcion del nuevo rol", "Por favor ingrese la descripcion del nuevo rol");
            isValid = false;
        }
        
        return isValid;
    }        
        
        
	public void guardar(ActionEvent ae){
		try{
                        if (!validar()){
                        return;
                        }
			Rol role = (Rol) this.getBean("#{role}", Rol.class);
			if (role.getCatRol().getCodRol()==null || role.getCatRol().getCodRol() == 0L){
				role.getCatRol().setCodRol(null);
				CatRoles newRole = roleSession.create(role.getCatRol());
				role.getCatRol().setCodRol(newRole.getCodRol());
                                this.addInfo("Se ha Guardado con Exito", "Se ha Guardado con Exito");
			}else{
				roleSession.update(role.getCatRol());
                                this.addInfo("Actualizado", "Actualizado");
			}
			rolesList = roleSession.findAllActiveRoles();
			this.setMenuList(new ArrayList<CatMenu>());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.getMessage());
		}
	}

	public void delete(ActionEvent ae){
		try{
			Rol role = (Rol) this.getBean("#{role}", Rol.class);
			roleSession.delete(role.getCatRol());
			role.getCatRol().setDesRol(null);
			role.getCatRol().setCodRol(null);
			role.getCatRol().setNomRol(null);
			role.getCatRol().setRolesMenu(new HashSet<CatRolesMenu>());
			rolesList = roleSession.findAllActiveRoles();
			this.setMenuList(new ArrayList<CatMenu>());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.getMessage());
		}		
	}
	
	public void nuevo(ActionEvent ae){
		try{
			Rol role = (Rol) this.getBean("#{role}", Rol.class);
			role.getCatRol().setCodRol(null);
			role.getCatRol().setDesRol(null);
			role.getCatRol().setNomRol(null);
			role.getCatRol().setRolesMenu(new HashSet<CatRolesMenu>());
			this.setMenuList(new ArrayList<CatMenu>());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.getMessage());
		}
	}
	
	public void seleccionar(ActionEvent ae){
		this.setMenuList(new ArrayList<CatMenu>());
	}	
}