/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.security;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.security.CatRoles;

/**
 *
 * @author Administrador
 */
@ManagedBean(name="role")
@ViewScoped
public class Rol implements Serializable{
    CatRoles catRol = new CatRoles();

    public CatRoles getCatRol() {
        return catRol;
    }

    public void setCatRol(CatRoles catRol) {
        this.catRol = catRol;
    }
}
