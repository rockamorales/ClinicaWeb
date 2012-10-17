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
import sv.com.cormaria.servicios.entidades.catalogos.CatBancos;
import sv.com.cormaria.servicios.facades.catalogos.CatBancosFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatBancosFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatBancos")
@RequestScoped
public class FrmMantCatBancos extends PageBase{
    private CatBancos catBancos = new CatBancos();
    
    @EJB
    private CatBancosFacadeLocal facade;
    @ManagedProperty(value ="#{param.codBanco}")
    private Integer codBanco;

    public Integer getCodBanco() {
        return codBanco;
    }

    public void setCodBanco(Integer codBanco) {
        if(codBanco != null){
            try{
            catBancos = facade.find(codBanco);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codBanco = codBanco;
    }
    
    
    
    public CatBancos getCatBancos() {
        return catBancos;
    }

    public void setCatBancos(CatBancos catBancos) {
        this.catBancos = catBancos;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatBancos().getNomBanco() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre del Banco", "Porfavor ingrese el nombre del Banco");
       }     
       return isValid;
   
   }
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }            
            if(catBancos.getCodBanco() != null){
                facade.edit(catBancos);
            }else{
                facade.create(catBancos);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catBancos = new CatBancos();
    }
    
    
}
