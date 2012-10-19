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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoReferencia;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoReferenciaFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatTipoReferencia")
@RequestScoped
public class FrmMantCatTipoReferencia extends PageBase{
    private CatTipoReferencia catTipoReferencia = new CatTipoReferencia();
    
    @EJB
    private CatTipoReferenciaFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipReferencia}")
    private Integer codTipReferencia;

    public Integer getCodTipReferencia() {
        return codTipReferencia;
    }

    public void setCodTipReferencia(Integer codTipReferencia) {
        if(codTipReferencia != null){
            try{
            catTipoReferencia = facade.find(codTipReferencia);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipReferencia = codTipReferencia;
    }
    
    
    
    public CatTipoReferencia getCatTipoReferencia() {
        return catTipoReferencia;
    }

    public void setCatTipoReferencia(CatTipoReferencia catTipoReferencia) {
        this.catTipoReferencia = catTipoReferencia;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatTipoReferencia().getNomTipReferencia() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre tipo de referencia", "Porfavor ingrese el nombre tipo de referencia");
       } 

       
       return isValid;
     
   }     
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }              
            if(catTipoReferencia.getCodTipReferencia() != null){
                facade.edit(catTipoReferencia);
            }else{
                facade.create(catTipoReferencia);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoReferencia = new CatTipoReferencia();
    }
    
    
}
