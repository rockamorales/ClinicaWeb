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
import sv.com.cormaria.servicios.entidades.catalogos.CatConsultorio;
import sv.com.cormaria.servicios.facades.catalogos.CatConsultorioFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatConsultorioFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatConsultorio")
@RequestScoped
public class FrmMantCatConsultorio extends PageBase{
    private CatConsultorio catConsultorio = new CatConsultorio();
    
    @EJB
    private CatConsultorioFacadeLocal facade;
    @ManagedProperty(value ="#{param.codConsultorio}")
    private Integer codConsultorio;

    public Integer getCodConsultorio() {
        return codConsultorio;
    }

    public void setCodConsultorio(Integer codConsultorio) {
        if(codConsultorio != null){
            try{
            catConsultorio = facade.find(codConsultorio);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codConsultorio = codConsultorio;
    }
    
    
    
    public CatConsultorio getCatConsultorio() {
        return catConsultorio;
    }

    public void setCatConsultorio(CatConsultorio catConsultorio) {
        this.catConsultorio = catConsultorio;
    }

       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatConsultorio().getNomConsultorio() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre del Consultorio", "Porfavor ingrese el nombre del Consultorio");
       } 

       
       return isValid;
     
   }      
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }             
            if(catConsultorio.getCodConsultorio() != null){
                facade.edit(catConsultorio);
            }else{
                facade.create(catConsultorio);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catConsultorio = new CatConsultorio();
    }
    
    
}
