/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.entidades.administracion.TblMovimientosExpediente;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoConsulta;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoConsultaFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblConsultasFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmGenerarConsulta extends PageBase{

    @EJB
    private TblConsultasFacadeLocal tblConsultasFacade;

    @EJB
    private TblExpedientePacientesFacadeLocal tblExpedienteFacade;
    
    private TblConsultas tblConsultas = new TblConsultas();
    
    private TblExpedientePacientes tblExpediente = new TblExpedientePacientes();
    
    @EJB
    private CatTipoConsultaFacadeLocal tipoConsultaFacade;   
    
    @EJB
    private CatEspecialidadFacadeLocal especialidadFacade;   

    @EJB
    private TblMedicoFacadeLocal medicosFacade;

    private List<SelectItem> catTipoConsultaList = new ArrayList<SelectItem>();
    private List<SelectItem> catEspecialidadList = new ArrayList<SelectItem>();
    private List<TblMedico> tblMedicosList = new ArrayList<TblMedico>();
    
    /**
     * Creates a new instance of FrmGenerarConsulta
     */
    public FrmGenerarConsulta() {
    }

    public TblConsultas getTblConsultas() {
        return tblConsultas;
    }

    public void setTblConsultas(TblConsultas tblConsultas) {
        this.tblConsultas = tblConsultas;
    }

    public TblExpedientePacientes getTblExpediente() {
        return tblExpediente;
    }

    public void setTblExpediente(TblExpedientePacientes tblExpediente) {
        this.tblExpediente = tblExpediente;
    }
    
    public List<SelectItem> getCatEspecialidadList() {
        if (this.catEspecialidadList.isEmpty()){
            try{
                List<CatEspecialidad> l = especialidadFacade.findActive();
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

    public List<SelectItem> getCatTipoConsultaList() {
        if (this.catTipoConsultaList.isEmpty()){
            try{
                List<CatTipoConsulta> l = tipoConsultaFacade.findActive();
                for (CatTipoConsulta catTipo : l) {
                    catTipoConsultaList.add(new SelectItem(catTipo.getCodTipConsulta(), catTipo.getNomTipConsulta()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return catTipoConsultaList;
    }

    public void setCatTipoConsultaList(List<SelectItem> catTipoConsultaList) {
        this.catTipoConsultaList = catTipoConsultaList;
    }

    public List<TblMedico> getTblMedicosList() {
        if (this.tblMedicosList.isEmpty()){
            try{
                tblMedicosList = medicosFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tblMedicosList;
    }

    public void setTblMedicosList(List<TblMedico> tblMedicosList) {
        this.tblMedicosList = tblMedicosList;
    }

       public boolean validar(){
       boolean isValid = true;
       
       if (this.getTblExpediente().getNumExpediente() == null || this.getTblExpediente().getNumExpediente() <= 0){
          isValid = false;
          this.addError("Porfavor ingrese o seleccione el número de Expediente de paciente", "Por favor ingrese o seleccione el número de Expediente de paciente");
       }
       
       if (this.getTblConsultas().getCodTipConsulta() == null || this.getTblConsultas().getCodTipConsulta() == -1){
           isValid = false;
           this.addError("Porfavor ingrese el tipo de Consulta", "Porfavor ingrese el tipo de Consulta");
       }
       
       if (this.getTblConsultas().getCodEspecialidad() == null || this.getTblConsultas().getCodEspecialidad() == -1){
           isValid = false;
           this.addError("Porfavor ingrese la especialidad", "Porfavor ingrese la especialidad");
       }
       
       if (this.getTblConsultas().getNumMedico() == null || this.getTblConsultas().getNumMedico() == -1){
           isValid = false;
           this.addError("Porfavor ingrese el Doctor", "Porfavor ingrese el Doctor");
       }
       
       return isValid;
       }
    
    public void generarConsulta(ActionEvent ae){
      try{
          if (!validar()){
              return;
           }
          if (tblConsultas.getNumConsulta()!=null && tblConsultas.getNumConsulta() > 0){
              //Si se actualiza la consulta, se tiene que considerar actualizar el comprobante de 
              //donacion asociado.
          }else{
            tblConsultas.setNumConsulta(null);
            tblConsultas.setNumExpediente(tblExpediente.getNumExpediente());
            tblConsultas = tblExpedienteFacade.generarConsulta(tblConsultas, this.getTblExpediente(), tblConsultas.getCodEspecialidad());
            this.addInfo("La consulta ha sido generada", "La consulta ha sido generada");
          }
      }catch(Exception ex){
         ex.printStackTrace();
         this.addError(ex.getMessage(), ex.getMessage());
      }
    }
    
    public void searchExpedienteByNum(){
      try{
          this.tblExpediente = tblExpedienteFacade.find(this.tblExpediente.getNumExpediente());
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }
  
   public void seleccionar(ActionEvent ae){
    try{
        UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
        System.out.println("Buscando el expediente... "+((TblExpedientePacientes)table.getRowData()).getNumExpediente());
        this.tblExpediente = tblExpedienteFacade.find(((TblExpedientePacientes)table.getRowData()).getNumExpediente());
        System.out.println("Expediente encontrado... ");
    }catch(Exception x){
        x.printStackTrace();
        this.addError(x.getMessage(), x.getMessage());
    }
  }
  
   public void buscar(ActionEvent ae){
      ExpedienteDataModel model = (ExpedienteDataModel) this.getBean("#{expedienteDataModel}", ExpedienteDataModel.class);
      model.clear();
   }
   
   public void nuevaConsulta(ActionEvent ae){
       this.tblExpediente = new TblExpedientePacientes();
       this.tblConsultas = new TblConsultas();
   }
}
