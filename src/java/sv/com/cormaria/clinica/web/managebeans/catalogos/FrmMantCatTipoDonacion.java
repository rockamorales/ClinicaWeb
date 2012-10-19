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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoDonacion;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonacionFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonacionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatTipoDonacion")
@RequestScoped
public class FrmMantCatTipoDonacion extends PageBase{
    private CatTipoDonacion catTipoDonacion = new CatTipoDonacion();
    
    @EJB
    private CatTipoDonacionFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipDonacion}")
    private Integer codTipDonacion;

    public Integer getCodTipDonacion() {
        return codTipDonacion;
    }

    public void setCodTipDonacion(Integer codTipDonacion) {
        if(codTipDonacion != null){
            try{
            catTipoDonacion = facade.find(codTipDonacion);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipDonacion = codTipDonacion;
    }
    
    
    
    public CatTipoDonacion getCatTipoDonacion() {
        return catTipoDonacion;
    }

    public void setCatTipoDonacion(CatTipoDonacion catTipoDonacion) {
        this.catTipoDonacion = catTipoDonacion;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatTipoDonacion().getNomTipDonacion() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre del tipo de donación", "Porfavor ingrese el nombre del tipo de donación");
       } 

       
       return isValid;
     
   }     
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }             
            if(catTipoDonacion.getCodTipDonacion() != null){
                facade.edit(catTipoDonacion);
            }else{
                facade.create(catTipoDonacion);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoDonacion = new CatTipoDonacion();
    }
    
    
}
