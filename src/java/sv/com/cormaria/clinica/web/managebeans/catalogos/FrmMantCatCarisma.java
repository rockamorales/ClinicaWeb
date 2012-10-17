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
import sv.com.cormaria.servicios.entidades.catalogos.CatCarisma;
import sv.com.cormaria.servicios.facades.catalogos.CatCarismaFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatCarismaFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatCarisma")
@RequestScoped
public class FrmMantCatCarisma extends PageBase{
    private CatCarisma catCarisma = new CatCarisma();
    
    @EJB
    private CatCarismaFacadeLocal facade;
    @ManagedProperty(value ="#{param.codCarisma}")
    private Integer codCarisma;

    public Integer getCodCarisma() {
        return codCarisma;
    }

    public void setCodCarisma(Integer codCarisma) {
        if(codCarisma != null){
            try{
            catCarisma = facade.find(codCarisma);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codCarisma = codCarisma;
    }
    
    
    
    public CatCarisma getCatCarisma() {
        return catCarisma;
    }

    public void setCatCarisma(CatCarisma catCarisma) {
        this.catCarisma = catCarisma;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatCarisma().getNomCarisma() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre de la entidad de la Fundación Carisma", "Porfavor ingrese el nombre de la entidad de la Fundación Carisma");
       } 

       
       return isValid;
     
   }    
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }            
            if(catCarisma.getCodCarisma() != null){
                facade.edit(catCarisma);
            }else{
                facade.create(catCarisma);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catCarisma = new CatCarisma();
    }
    
    
}
