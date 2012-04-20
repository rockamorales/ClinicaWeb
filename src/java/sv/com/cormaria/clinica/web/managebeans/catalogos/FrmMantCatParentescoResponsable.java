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
import sv.com.cormaria.servicios.entidades.catalogos.CatParentescoResponsable;
import sv.com.cormaria.servicios.facades.catalogos.CatParentescoResponsableFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatParentescoResponsableFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatParentescoResponsable")
@RequestScoped
public class FrmMantCatParentescoResponsable extends PageBase{
    private CatParentescoResponsable catParentescoResponsable = new CatParentescoResponsable();
    
    @EJB
    private CatParentescoResponsableFacadeLocal facade;
    @ManagedProperty(value ="#{param.codParResponsable}")
    private Integer codParResponsable;

    public Integer getCodParResponsable() {
        return codParResponsable;
    }

    public void setCodParResponsable(Integer codParResponsable) {
        if(codParResponsable != null){
            try{
            catParentescoResponsable = facade.find(codParResponsable);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codParResponsable = codParResponsable;
    }
    
    
    
    public CatParentescoResponsable getCatParentescoResponsable() {
        return catParentescoResponsable;
    }

    public void setCatParentescoResponsable(CatParentescoResponsable catParentescoResponsable) {
        this.catParentescoResponsable = catParentescoResponsable;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catParentescoResponsable.getCodParResponsable() != null){
                facade.edit(catParentescoResponsable);
            }else{
                facade.create(catParentescoResponsable);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catParentescoResponsable = new CatParentescoResponsable();
    }
    
    
}
