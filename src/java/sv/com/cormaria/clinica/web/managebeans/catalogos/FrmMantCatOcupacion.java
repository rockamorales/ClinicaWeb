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
import sv.com.cormaria.servicios.entidades.catalogos.CatOcupacion;
import sv.com.cormaria.servicios.facades.catalogos.CatOcupacionFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatOcupacionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatOcupacion")
@RequestScoped
public class FrmMantCatOcupacion extends PageBase{
    private CatOcupacion catOcupacion = new CatOcupacion();
    
    @EJB
    private CatOcupacionFacadeLocal facade;
    @ManagedProperty(value ="#{param.codOcupacion}")
    private Integer codOcupacion;

    public Integer getCodOcupacion() {
        return codOcupacion;
    }

    public void setCodOcupacion(Integer codOcupacion) {
        if(codOcupacion != null){
            try{
            catOcupacion = facade.find(codOcupacion);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codOcupacion = codOcupacion;
    }
    
    
    
    public CatOcupacion getCatOcupacion() {
        return catOcupacion;
    }

    public void setCatOcupacion(CatOcupacion catOcupacion) {
        this.catOcupacion = catOcupacion;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatOcupacion().getNomOcupacion() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre de la Ocupación", "Porfavor ingrese el nombre de la Ocupación");
       } 

       
       return isValid;
     
   } 
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }             
            if(catOcupacion.getCodOcupacion() != null){
                facade.edit(catOcupacion);
            }else{
                facade.create(catOcupacion);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catOcupacion = new CatOcupacion();
    }
    
    
}
