/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.security;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.security.CatMenu;

/**
 *
 * @author Administrador
 */
@ManagedBean
@ViewScoped
public class Menu implements Serializable{
    private CatMenu catMenu = new CatMenu();

    public CatMenu getCatMenu() {
        return catMenu;
    }

    public void setCatMenu(CatMenu catMenu) {
        this.catMenu = catMenu;
    }    
}
