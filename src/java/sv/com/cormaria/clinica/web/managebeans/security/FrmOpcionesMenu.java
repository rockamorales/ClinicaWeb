package sv.com.cormaria.clinica.web.managebeans.security;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.richfaces.component.UIDataTable;
import org.richfaces.component.UITree;
import org.richfaces.event.DropEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.security.CatMenu;
import sv.com.cormaria.servicios.entidades.security.CatUrlMenu;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;
import sv.com.cormaria.servicios.facades.security.CatMenuSessionFacadeLocal;
import sv.com.cormaria.servicios.facades.security.UrlsmenuSessionFacadeLocal;

@ManagedBean(name="frmOpcionesMenu")
@RequestScoped
public class FrmOpcionesMenu extends PageBase {
	@EJB
	CatMenuSessionFacadeLocal menuSession;

	@EJB
	UrlsmenuSessionFacadeLocal urlMenuSession;
		
	public List<CatMenu> menuList = new ArrayList<CatMenu>();
	public List<CatUrlMenu> urlsList = new ArrayList<CatUrlMenu>();
	
	public List<CatUrlMenu> getUrlsList() {
		/*if (urlsList.isEmpty()){
			CatMenu menu = (CatMenu) this.getBean("#{menu}", CatMenu.class);
			try {
				urlsList = urlMenuSession.findByCodigomenu(menu.getCodMenu());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		return urlsList;
	}

	public void setUrlsList(List<CatUrlMenu> urlsList) {
		this.urlsList = urlsList;
	}

	private List<CatMenu> getStructuredMenu(List<CatMenu> menuList){
		List<CatMenu> options = new ArrayList<CatMenu>();
		for (CatMenu menu : menuList){
                        System.out.println("Generando menu: "+menu.getNomOpcMenu());
			if (menu.getCodPadMenu() == null){
				options.add(menu);
			}else{
				CatMenu menupadre = menu.getMenu();
				if (menupadre!=null){
					menupadre.getOptions().add(menu);
				}
			}
		}
		return options;
	}
		
	public List<CatMenu> getMenuList() {
		if (menuList.isEmpty()){
			try {
                                System.out.println("Generando el menu");
				menuList = getStructuredMenu(menuSession.findAllMenu());
                                System.out.println("Menu List Size: "+menuList.size());
			} catch (ClinicaModelexception e) {
				e.printStackTrace();
			}
		}
		return menuList;
	}

	public void setMenuList(List<CatMenu> menuList) {
		this.menuList = menuList;
	}
	
	public void guardar(ActionEvent ae){
		try{
			Menu menu = (Menu) this.getBean("#{menu}", Menu.class);
			if (!this.validate(menu.getCatMenu())){
				return;
			}
			if (menu.getCatMenu()!=null && menu.getCatMenu().getCodMenu()!=null && menu.getCatMenu().getCodMenu()>0){
				if (menu.getCatMenu().getCodPadMenu()!= null && menu.getCatMenu().getCodPadMenu()<=0){
					menu.getCatMenu().setCodPadMenu(null);
				}
				menuSession.update(menu.getCatMenu());
			}else{
                                System.out.println("Cod pad menu: "+menu.getCatMenu().getCodPadMenu());
				if (menu.getCatMenu().getCodPadMenu()<=0){
					menu.getCatMenu().setCodPadMenu(null);
				}
				menu.getCatMenu().setCodMenu(null);
				CatMenu createdMenu = menuSession.create(menu.getCatMenu());
				menu.getCatMenu().setCodMenu(createdMenu.getCodMenu());
			}
			this.menuList.clear();
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.getMessage());
		}
	}
	
	public void seleccionar(ActionEvent ae){
                System.out.println("Seleccionando");
		this.setUrlsList(new ArrayList<CatUrlMenu>());
	}

	public void delete(ActionEvent ae){
		try{
			UITree node = (UITree)ae.getComponent().getParent().getParent().getParent().getParent();
			CatMenu menu = (CatMenu)node.getRowData();
                        System.out.println("Deleting: "+menu.getCodMenu());
			menuSession.delete(menu.getCodMenu());
			CatMenu menu1 = ((Menu) this.getBean("#{menu}", Menu.class)).getCatMenu();
			menu1.setCodMenu(null);
			menu1.setIcoMenu(null);
			menu1.setNomOpcMenu(null);
			menu1.setUrlIniMenu(null);
			this.getMenuList().clear();
			this.setUrlsList(new ArrayList<CatUrlMenu>());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.getMessage());
		}		
	}

	public void deleteUrl(ActionEvent ae){
		try{
			UIDataTable table = (UIDataTable)ae.getComponent().getParent().getParent();
			CatUrlMenu urlMenu = (CatUrlMenu)table.getRowData();
			urlMenuSession.delete(urlMenu.getCodUrlMenu());
			this.setUrlsList(new ArrayList<CatUrlMenu>());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.getMessage());
		}		
	}
	
	
	public void nuevo(ActionEvent ae){
		try{
                    System.out.println("Nuevo registro");
                    Menu menu = (Menu) this.getBean("#{menu}", Menu.class);
                    System.out.println("Codigo pad menu: "+menu.getCatMenu().getCodPadMenu());
                    menu.getCatMenu().setCodMenu(null);
                    menu.getCatMenu().setIcoMenu(null);
                    menu.getCatMenu().setNomOpcMenu(null);
                    menu.getCatMenu().setUrlIniMenu(null);
                    menu.getCatMenu().setMenu(null);
                    this.setUrlsList(new ArrayList<CatUrlMenu>());
		}catch(Exception ex){
			ex.printStackTrace();
			this.addError(ex.getMessage(), ex.getMessage());
		}
	}
	
	private boolean validate(CatMenu menu){
		boolean isValid = true;
		if (menu.getNomOpcMenu()==null || menu.getNomOpcMenu().trim().equals("")){
			this.addError("Por favor ingrese el nombre del menu", "Por favor ingrese el nombre del menu");
			isValid = false;
		}
		return isValid;
	}
		
	public void dropListener(DropEvent dropEvent) {
	}
}