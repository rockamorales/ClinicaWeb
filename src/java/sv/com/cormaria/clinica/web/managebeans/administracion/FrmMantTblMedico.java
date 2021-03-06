/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.administracion;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoServicio;
import sv.com.cormaria.servicios.enums.Estado;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacade;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServicioFacadeLocal;
import sv.com.cormaria.servicios.helpers.ValidationUtils;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantTblMedico")
@RequestScoped
public class FrmMantTblMedico extends PageBase{
    private TblMedico tblMedico = new TblMedico();
    
    @EJB
    private TblMedicoFacadeLocal facade;
    @EJB
    private CatTipoServicioFacadeLocal catTipoServicioFacade;
    @EJB
    private CatEspecialidadFacadeLocal catEspecialidadFacade;
    @ManagedProperty(value ="#{param.numMedico}")
    private Integer numMedico;
    private List<SelectItem> catTipoServicioList = new ArrayList<SelectItem>();
    private List<SelectItem> catEspecialidadList = new ArrayList<SelectItem>();

    public List<SelectItem> getCatEspecialidadList() {
          if (catEspecialidadList.isEmpty()){
            try{
                List<CatEspecialidad> l = catEspecialidadFacade.findActive();
                for (CatEspecialidad catEspecialidad : l) {
                    catEspecialidadList.add(new SelectItem(catEspecialidad.getCodEspecialidad(), catEspecialidad.getNomEspecialidad()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }         
        return catEspecialidadList;
    }

    public void setCatEspecialidadList(List<SelectItem> catEspecialidadList) {
        this.catEspecialidadList = catEspecialidadList;
    }

    public List<SelectItem> getCatTipoServicioList() {
          if (catTipoServicioList.isEmpty()){
            try{
                List<CatTipoServicio> l = catTipoServicioFacade.findActive();
                for (CatTipoServicio catTipoServicio : l) {
                    catTipoServicioList.add(new SelectItem(catTipoServicio.getCodTipServicio(), catTipoServicio.getNomTipServicio()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }        
        return catTipoServicioList;
    }

    public void setCatTipoServicioList(List<SelectItem> catTipoServicioList) {
        this.catTipoServicioList = catTipoServicioList;
    }
    

    public Integer getNumMedico() {
        return numMedico;
    }

    public void setNumMedico(Integer numMedico) {
        if(numMedico != null){
            try{
            tblMedico = facade.find(numMedico);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.numMedico = numMedico;
    }
    
    
    
    public TblMedico getTblMedico() {
        return tblMedico;
    }

    public void setTblMedico(TblMedico tblMedico) {
        this.tblMedico = tblMedico;
    }
    
    
   public boolean validar(){
       boolean isValid = true;
       
       if (this.getTblMedico().getNomMedico() == null || this.getTblMedico().getNomMedico().trim().equals("")) {
          isValid = false;
          this.addError("Porfavor ingrese los nombres del médico", "Porfavor ingrese los nombres del médico");
       }     
       if (this.getTblMedico().getPriApeMedico() == null || this.getTblMedico().getPriApeMedico().trim().equals("")) {
          isValid = false;
          this.addError("Porfavor ingrese el primer apellido del médico", "Porfavor ingrese el primer apellido del médico");
       }          
       if (this.getTblMedico().getCodTipServicio() == null || this.getTblMedico().getCodTipServicio() <= 0){
          isValid = false;
          this.addError("Porfavor seleccione el tipo de servicio", "Porfavor seleccione el tipo de servicio");
       } 
       if (this.getTblMedico().getCodEspecialidad() == null || this.getTblMedico().getCodEspecialidad() <= 0){
           isValid = false;
           this.addError("Porfavor seleccione la especialidad", "Porfavor seleccione la especialidad");
       }
       if (this.getTblMedico().getNumJunta() <= 0){
          isValid = false;
          this.addError("Porfavor ingrese el numero de junta del médico", "Porfavor ingrese el numero de junta del médico");
       }    
       if (this.getTblMedico().getFecIngMedico() == null){
           isValid = false;
           this.addError("Porfavor seleccione la fecha de ingreso", "Porfavor seleccione la fecha de ingreso");
       }       
       if (this.getTblMedico().getDirPerMedico() == null || this.getTblMedico().getDirPerMedico().trim().equals("")) {
          isValid = false;
          this.addError("Porfavor ingrese la dirección personal del médico", "Porfavor ingrese la dirección personal del médico");
       }  
       if (this.getTblMedico().getDuiMedico() <= 0){
          isValid = false;
          this.addError("Porfavor ingrese el DUI del médico", "Porfavor ingrese el DUI del médico");
       }  
       if (this.getTblMedico().getNitMedico() <= 0){
            isValid = false;
           this.addError("Porfavor ingrese el número de NIT del Beneficiario", "Porfavor ingrese el número de DUI del Beneficiario");
       }    
       if (this.getTblMedico().getValConMedico() <= 0){
            isValid = false;
           this.addError("Porfavor ingrese el valor de la consulta del médico", "Porfavor ingrese el valor de la consulta del médico");
       }        
       
      
       return isValid;
     
   }    
    
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }            
            if(tblMedico.getNumMedico() != null){
                facade.edit(tblMedico);
            }else{
                facade.create(tblMedico);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblMedico = new TblMedico();
    }
    
}
