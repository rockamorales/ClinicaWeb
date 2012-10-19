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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoDonante;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonanteFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonanteFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmMantCatTipoDonante")
@RequestScoped
public class FrmMantCatTipoDonante extends PageBase{
    private CatTipoDonante catTipoDonante = new CatTipoDonante();
    
    @EJB
    private CatTipoDonanteFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipoDonante}")
    private Integer codTipoDonante;

    public Integer getCodTipoDonante() {
        return codTipoDonante;
    }

    public void setCodTipoDonante(Integer codTipoDonante) {
        if(codTipoDonante != null){
            try{
            catTipoDonante = facade.find(codTipoDonante);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipoDonante = codTipoDonante;
    }
    
    
    
    public CatTipoDonante getCatTipoDonante() {
        return catTipoDonante;
    }

    public void setCatTipoDonante(CatTipoDonante catTipoDonante) {
        this.catTipoDonante = catTipoDonante;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatTipoDonante().getNomTipDonante() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre del tipo de donante", "Porfavor ingrese el nombre del tipo de donante");
       } 

       
       return isValid;
     
   }     
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }              
            if(catTipoDonante.getCodTipDonante() != null){
                facade.edit(catTipoDonante);
            }else{
                facade.create(catTipoDonante);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoDonante = new CatTipoDonante();
    }
    
    
}
