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
import sv.com.cormaria.servicios.entidades.catalogos.CatEstadoCivil;
import sv.com.cormaria.servicios.facades.catalogos.CatEstadoCivilFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatEstadoCivilFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatEstadoCivil")
@RequestScoped
public class FrmMantCatEstadoCivil extends PageBase{
    private CatEstadoCivil catEstadoCivil = new CatEstadoCivil();
    
    @EJB
    private CatEstadoCivilFacadeLocal facade;
    @ManagedProperty(value ="#{param.codEstCivil}")
    private Integer codEstCivil;

    public Integer getCodEstCivil() {
        return codEstCivil;
    }

    public void setCodEstCivil(Integer codEstCivil) {
        if(codEstCivil != null){
            try{
            catEstadoCivil = facade.find(codEstCivil);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codEstCivil = codEstCivil;
    }
    
    
    
    public CatEstadoCivil getCatEstadoCivil() {
        return catEstadoCivil;
    }

    public void setCatEstadoCivil(CatEstadoCivil catEstadoCivil) {
        this.catEstadoCivil = catEstadoCivil;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catEstadoCivil.getCodEstCivil() != null){
                facade.edit(catEstadoCivil);
            }else{
                facade.create(catEstadoCivil);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catEstadoCivil = new CatEstadoCivil();
    }
    
    
}
