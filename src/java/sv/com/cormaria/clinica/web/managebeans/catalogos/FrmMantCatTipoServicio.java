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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoServicio;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServicioFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServicioFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatTipoServicio")
@RequestScoped
public class FrmMantCatTipoServicio extends PageBase{
    private CatTipoServicio catTipoServicio = new CatTipoServicio();
    
    @EJB
    private CatTipoServicioFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipServicio}")
    private Integer codTipServicio;

    public Integer getCodTipServicio() {
        return codTipServicio;
    }

    public void setCodTipServicio(Integer codTipServicio) {
        if(codTipServicio != null){
            try{
            catTipoServicio = facade.find(codTipServicio);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipServicio = codTipServicio;
    }
    
    
    
    public CatTipoServicio getCatTipoServicio() {
        return catTipoServicio;
    }

    public void setCatTipoServicio(CatTipoServicio catTipoServicio) {
        this.catTipoServicio = catTipoServicio;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatTipoServicio().getNomTipServicio() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre del tipo de servicio", "Porfavor ingrese el nombre del tipo de servicio");
       } 

       
       return isValid;
     
   }      
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }             
            if(catTipoServicio.getCodTipServicio() != null){
                facade.edit(catTipoServicio);
            }else{
                facade.create(catTipoServicio);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoServicio = new CatTipoServicio();
    }
    
    
}
