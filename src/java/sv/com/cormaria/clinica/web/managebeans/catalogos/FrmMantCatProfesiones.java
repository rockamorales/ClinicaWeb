/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatProfesiones;
import sv.com.cormaria.servicios.facades.catalogos.CatProfesionesFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatProfesionesFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatProfesiones")
@RequestScoped
public class FrmMantCatProfesiones extends PageBase{
    private CatProfesiones catProfesiones = new CatProfesiones();
    
    @EJB
    private CatProfesionesFacadeLocal facade;
    @ManagedProperty(value ="#{param.codProfesion}")
    private Integer codProfesion;

    public Integer getCodProfesion() {
        return codProfesion;
    }

    public void setCodProfesion(Integer codProfesion) {
        if(codProfesion != null){
            try{
            catProfesiones = facade.find(codProfesion);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codProfesion = codProfesion;
    }
    
    
    
    public CatProfesiones getCatProfesiones() {
        return catProfesiones;
    }

    public void setCatProfesiones(CatProfesiones catProfesiones) {
        this.catProfesiones = catProfesiones;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catProfesiones.getCodProfesion() != null){
                facade.edit(catProfesiones);
            }else{
                facade.create(catProfesiones);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catProfesiones = new CatProfesiones();
    }
    
    
}
