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
import sv.com.cormaria.servicios.entidades.administracion.TblEmpleado;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoServicio;
import sv.com.cormaria.servicios.entidades.catalogos.CatAreas;
import sv.com.cormaria.servicios.entidades.administracion.TblInstitucion;
import sv.com.cormaria.servicios.entidades.catalogos.CatProfesiones;
import sv.com.cormaria.servicios.enums.Estado;
import sv.com.cormaria.servicios.facades.administracion.TblEmpleadoFacade;
import sv.com.cormaria.servicios.facades.administracion.TblEmpleadoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServicioFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatAreasFacadeLocal;
import sv.com.cormaria.servicios.facades.administracion.TblInstitucionFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatProfesionesFacadeLocal;

/**
 *
 * @author Claudia
 */
@ManagedBean (name = "frmMantTblEmpleado")
@RequestScoped
public class FrmMantTblEmpleado extends PageBase{
    private TblEmpleado tblEmpleado = new TblEmpleado();

    @EJB
    private TblEmpleadoFacadeLocal facade;
    @EJB
    private CatTipoServicioFacadeLocal catTipoServicioFacade;
    @EJB
    private CatProfesionesFacadeLocal catProfesionesFacade;
    
    @EJB
    private CatAreasFacadeLocal catAreasFacade;
    @EJB
    private TblInstitucionFacadeLocal tblInstitucionFacade;
    
    
    @ManagedProperty(value ="#{param.numEmpleado}")
    private Integer numEmpleado;
    private List<SelectItem> catTipoServicioList = new ArrayList<SelectItem>();
    private List<SelectItem> catAreasList = new ArrayList<SelectItem>();
    private List<SelectItem> catProfesionesList = new ArrayList<SelectItem>();
    private List<SelectItem> tblInstitucionList = new ArrayList<SelectItem>();

    public List<SelectItem> getTblInstitucionList() {
        if (tblInstitucionList.isEmpty()){
            try{
                List<TblInstitucion> l =tblInstitucionFacade.findActive();
                for (TblInstitucion tblInstitucion : l) {
                   tblInstitucionList.add(new SelectItem(tblInstitucion.getNumInstitucion(), tblInstitucion.getNomComInstitucion()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tblInstitucionList;
    }

    public void setTblInstitucionList(List<SelectItem> tblInstitucionList) {
        this.tblInstitucionList = tblInstitucionList;
    }

    public List<SelectItem> getCatProfesionesList() {
        if (catProfesionesList.isEmpty()){
            try{
                List<CatProfesiones> l =catProfesionesFacade.findActive();
                for (CatProfesiones catProfesiones : l) {
                   catProfesionesList.add(new SelectItem(catProfesiones.getCodProfesion(), catProfesiones.getNomProfesion()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
       
        return catProfesionesList;
    }

    public void setCatProfesionesList(List<SelectItem> catProfesionesList) {
        this.catProfesionesList = catProfesionesList;
    }

    
    public List<SelectItem> getCatAreasList() {
        if (catAreasList.isEmpty()){
            try{
                List<CatAreas> l = catAreasFacade.findActive();
                for (CatAreas catArea : l) {
                    catAreasList.add(new SelectItem(catArea.getCodArea(), catArea.getNomArea()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return catAreasList;
    }

    public void setCatAreasList(List<SelectItem> catAreasList) {
        this.catAreasList = catAreasList;
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
    
    
    
    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Integer numEmpleado) {
        if(numEmpleado != null){
            try{
            tblEmpleado = facade.find(numEmpleado);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.numEmpleado = numEmpleado;
    }
    
    
    
    public TblEmpleado getTblEmpleado() {
        return tblEmpleado;
    }

    public void setTblEmpleado(TblEmpleado tblEmpleado) {
        this.tblEmpleado = tblEmpleado;
    }
    
    public boolean validar(){
       boolean isValid = true;
       
       if (this.getTblEmpleado().getCodTipServicio() == null || this.getTblEmpleado().getCodTipServicio() <=0 ) {
          isValid = false;
          this.addError("Porfavor seleccione el tipo de servicio", "Porfavor seleccione el tipo de servicio");
       } 
       
       if (this.getTblEmpleado().getCodArea() == null || this.getTblEmpleado().getCodArea() <= 0){
           isValid = false;
           this.addError("Por favor seleccione el Area", "Por favor seleccione el Area");
       }
       if (this.getTblEmpleado().getCodProfesion() == null || this.getTblEmpleado().getCodProfesion() <= 0){
           isValid = false;
           this.addError("Por favor seleccione Profesion del Empleado", "Por favor seleccione Profesion del Empleado");
       }
    
       return isValid;
  }    
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }  
       
            if(tblEmpleado.getNumEmpleado() != null){
                facade.edit(tblEmpleado);
            }else{
                facade.create(tblEmpleado);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblEmpleado = new TblEmpleado();
    }
    
    
}
