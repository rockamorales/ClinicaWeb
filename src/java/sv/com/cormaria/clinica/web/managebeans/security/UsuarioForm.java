/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.security;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.security.TblUsuarios;

/**
 *
 * @author Administrador
 */
@ManagedBean(name = "usuario")
@ViewScoped
public class UsuarioForm implements Serializable{
    private TblUsuarios tblUsuario = new TblUsuarios();

    public UsuarioForm(){
    }
    
    public TblUsuarios getTblUsuario() {
        return tblUsuario;
    }

    public void setTblUsuario(TblUsuarios tblUsuario) {
        this.tblUsuario = tblUsuario;
    }
}