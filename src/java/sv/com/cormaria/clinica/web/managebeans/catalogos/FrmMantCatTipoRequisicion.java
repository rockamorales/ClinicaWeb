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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoRequisicion;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoRequisicionFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoRequisicionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatTipoRequisicion")
@RequestScoped
public class FrmMantCatTipoRequisicion extends PageBase{
    private CatTipoRequisicion catTipoRequisicion = new CatTipoRequisicion();
    
    @EJB
    private CatTipoRequisicionFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipRequisicion}")
    private Integer codTipRequisicion;

    public Integer getCodTipRequisicion() {
        return codTipRequisicion;
    }

    public void setCodTipRequisicion(Integer codTipRequisicion) {
        if(codTipRequisicion != null){
            try{
            catTipoRequisicion = facade.find(codTipRequisicion);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipRequisicion = codTipRequisicion;
    }
    
    
    
    public CatTipoRequisicion getCatTipoRequisicion() {
        return catTipoRequisicion;
    }

    public void setCatTipoRequisicion(CatTipoRequisicion catTipoRequisicion) {
        this.catTipoRequisicion = catTipoRequisicion;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catTipoRequisicion.getCodTipRequisicion() != null){
                facade.edit(catTipoRequisicion);
            }else{
                facade.create(catTipoRequisicion);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoRequisicion = new CatTipoRequisicion();
    }
    
    
}
