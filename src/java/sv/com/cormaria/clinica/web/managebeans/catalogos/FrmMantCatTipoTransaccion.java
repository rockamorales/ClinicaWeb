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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoTransaccion;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoTransaccionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatTipoTransaccion")
@RequestScoped
public class FrmMantCatTipoTransaccion extends PageBase{
    private CatTipoTransaccion catTipoTransaccion = new CatTipoTransaccion();
    
    @EJB
    private CatTipoTransaccionFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipoTransaccion}")
    private Integer codTipoTransaccion;

    public Integer getCodTipoTransaccion() {
        return codTipoTransaccion;
    }

    public void setCodTipoTransaccion(Integer codTipoTransaccion) {
        if(codTipoTransaccion != null){
            try{
            catTipoTransaccion = facade.find(codTipoTransaccion);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipoTransaccion = codTipoTransaccion;
    }
    
    
    
    public CatTipoTransaccion getCatTipoTransaccion() {
        return catTipoTransaccion;
    }

    public void setCatTipoTransaccion(CatTipoTransaccion catTipoTransaccion) {
        this.catTipoTransaccion = catTipoTransaccion;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatTipoTransaccion().getNomTipTransaccion() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre del tipo de transacción", "Porfavor ingrese el nombre del tipo de transacción");
       } 

       
       return isValid;
     
   }      
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }              
            if(catTipoTransaccion.getCodTipTransaccion() != null){
                facade.edit(catTipoTransaccion);
            }else{
                facade.create(catTipoTransaccion);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoTransaccion = new CatTipoTransaccion();
    }
    
    
}
