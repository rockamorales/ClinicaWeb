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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoSalida;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoSalidaFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoSalidaFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatTipoSalida")
@RequestScoped
public class FrmMantCatTipoSalida extends PageBase{
    private CatTipoSalida catTipoSalida = new CatTipoSalida();
    
    @EJB
    private CatTipoSalidaFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipSalida}")
    private Integer codTipSalida;

    public Integer getCodTipSalida() {
        return codTipSalida;
    }

    public void setCodTipSalida(Integer codTipSalida) {
        if(codTipSalida != null){
            try{
            catTipoSalida = facade.find(codTipSalida);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipSalida = codTipSalida;
    }
    
    
    
    public CatTipoSalida getCatTipoSalida() {
        return catTipoSalida;
    }

    public void setCatTipoSalida(CatTipoSalida catTipoSalida) {
        this.catTipoSalida = catTipoSalida;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatTipoSalida().getNomTipSalida() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre del tipo de salida", "Porfavor ingrese el nombre del tipo de salida");
       } 

       
       return isValid;
     
   }      
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }             
            if(catTipoSalida.getCodTipSalida() != null){
                facade.edit(catTipoSalida);
            }else{
                facade.create(catTipoSalida);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoSalida = new CatTipoSalida();
    }
    
    
}
