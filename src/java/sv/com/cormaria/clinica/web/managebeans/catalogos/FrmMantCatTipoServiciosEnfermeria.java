/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoServiciosEnfermeria;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServiciosEnfermeriaFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatTipoServiciosEnfermeria")
@RequestScoped
public class FrmMantCatTipoServiciosEnfermeria extends PageBase{
    private CatTipoServiciosEnfermeria catTipoServiciosEnfermeria = new CatTipoServiciosEnfermeria();
    
    @EJB
    private CatTipoServiciosEnfermeriaFacadeLocal facade;
    @ManagedProperty(value ="#{param.codSerEnfermeria}")
    private Integer codSerEnfermeria;

    public Integer getCodSerEnfermeria() {
        return codSerEnfermeria;
    }

    public void setCodSerEnfermeria(Integer codSerEnfermeria) {
        if(codSerEnfermeria != null){
            try{
            catTipoServiciosEnfermeria = facade.find(codSerEnfermeria);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codSerEnfermeria = codSerEnfermeria;
    }
    
    
    
    public CatTipoServiciosEnfermeria getCatTipoServiciosEnfermeria() {
        return catTipoServiciosEnfermeria;
    }

    public void setCatTipoServiciosEnfermeria(CatTipoServiciosEnfermeria catTipoServiciosEnfermeria) {
        this.catTipoServiciosEnfermeria = catTipoServiciosEnfermeria;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catTipoServiciosEnfermeria.getCodSerEnfermeria() != null){
                facade.edit(catTipoServiciosEnfermeria);
            }else{
                facade.create(catTipoServiciosEnfermeria);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoServiciosEnfermeria = new CatTipoServiciosEnfermeria();
    }
    
    
}

