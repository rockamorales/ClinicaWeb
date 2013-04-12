/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.archivo.TblProgramacionCitas;
import sv.com.cormaria.servicios.entidades.catalogos.CatConsultorio;
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.facades.administracion.TblEmpleadoFacadeLocal;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblProgramacionCitasFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatConsultorioFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmMantTblProgramacionCitas extends PageBase{

    /**
     * Creates a new instance of FrmMantTblProgramacionCitas
     */
    public FrmMantTblProgramacionCitas() {
    }
    
    @EJB
    private TblProgramacionCitasFacadeLocal citasFacade;
    
    @EJB
    private TblExpedientePacientesFacadeLocal expedienteFacade;

    @EJB
    private CatEspecialidadFacadeLocal especialidadFacade;

    @EJB
    private TblEmpleadoFacadeLocal empleadoFacade;

    @EJB
    private CatConsultorioFacadeLocal consultorioFacade;

    @EJB
    private TblMedicoFacadeLocal medicoFacade;
    
    private Integer numCita;
    private String fecCita;
    private String horCita;
    private TblExpedientePacientes expediente = new TblExpedientePacientes();
    private TblProgramacionCitas cita = new TblProgramacionCitas();
    private List<CatEspecialidad> especialidadList = new ArrayList<CatEspecialidad>();
    private List<CatConsultorio> consultoriosList = new ArrayList<CatConsultorio>();
    private List<TblMedico> medicosList = new ArrayList<TblMedico>();
    private boolean applied = false;

    public String getFecCita() {
        return fecCita;
    }

    public void setFecCita(String fecCita) {
        this.fecCita = fecCita;
    }

    public String getHorCita() {
        return horCita;
    }

    public void setHorCita(String horCita) {
        this.horCita = horCita;
    }

    public Integer getNumCita() {
        return numCita;
    }

    public void setNumCita(Integer numCita) {
        this.numCita = numCita;
    }

    public List<CatEspecialidad> getEspecialidadList() {
        if (especialidadList.isEmpty()){
            try{
                especialidadList = especialidadFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return especialidadList;
    }

    public void setEspecialidadList(List<CatEspecialidad> especialidadList) {
        this.especialidadList = especialidadList;
    }

    public List<CatConsultorio> getConsultoriosList() {
        if (consultoriosList.isEmpty()){
            try{
                consultoriosList = consultorioFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }            
        }
        return consultoriosList;
    }

    public void setConsultoriosList(List<CatConsultorio> consultoriosList) {
        this.consultoriosList = consultoriosList;
    }

    public List<TblMedico> getMedicosList() {
        if (medicosList.isEmpty()){
            try{
                medicosList = medicoFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return medicosList;
    }

    public void setMedicosList(List<TblMedico> medicosList) {
        this.medicosList = medicosList;
    }
    
    public TblExpedientePacientes getExpediente() {
        return expediente;
    }

    public void setExpediente(TblExpedientePacientes expediente) {
        this.expediente = expediente;
    }

    public TblProgramacionCitas getCita() {
        return cita;
    }

    public void setCita(TblProgramacionCitas cita) {
        this.cita = cita;
    }
    
    public void init(){
        if (!applied){
            if (numCita!=null && numCita > 0){
                try{
                    cita = citasFacade.find(numCita);
                    expediente = cita.getExpediente();
                }catch(Exception ex){
                    ex.printStackTrace();
                    this.addError(ex.getMessage(), ex.getMessage());
                }
            }else{
                try{
                  if (fecCita!=null && !fecCita.trim().equals("")){
                      SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                      cita.setFecCita(format.parse(fecCita));
                  }
                  if (horCita!=null && !horCita.trim().equals("")){
                      SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                      cita.setHorCita(format.parse(horCita));

                  }
                }catch(Exception ex){
                    //Me vale madre si hay un error
                }
            }
            applied = true;
       }
    }    

    public void seleccionar(ActionEvent ae){
       try{
           UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
           System.out.println("Buscando el expediente... "+((TblExpedientePacientes)table.getRowData()).getNumExpediente());
           this.expediente = expedienteFacade.find(((TblExpedientePacientes)table.getRowData()).getNumExpediente());
           this.cita.setNumExpediente(expediente.getNumExpediente());
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

    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCita().getCodEspecialidad() == null || this.getCita().getCodEspecialidad() <= 0){
          isValid = false;
          this.addError("Por favor ingrese o seleccione la especialidad", "Por favor ingrese o seleccione la especialidad");
       } 
       
       if (this.getCita().getCodConsultorio() == null || this.getCita().getCodConsultorio() == -1){
           isValid = false;
           this.addError("Por favor seleccione el consultorio", "Por favor seleccione el consultorio");
       }
       
       if (this.getCita().getNumMedico() == null || this.getCita().getNumMedico() == -1){
           isValid = false;
           this.addError("Por favor seleccione el médico", "Por favor seleccione el médico");
       }
       
       if (this.getCita().getHorCita() == null) {
           isValid = false;
           this.addError("Por favor ingrese la Hora de la cita", "Por favor ingrese la Hora de la cita");
       }
       if (this.getCita().getFecCita() == null) {
           isValid = false;
           this.addError("Por favor seleccione la Fecha de la cita", "Por favor seleccione la Fecha de la cita");
       }
       if (this.getCita().getNumExpediente() == null) {
           isValid = false;
           this.addError("Por favor seleccione el numero de Expediente", "Por favor seleccione el numero de Expediente");
       }
       
       return isValid;
     
   }
    
    
    public String guardar(){
        try{
           if (!validar()){
               return null;
           }            
            if (cita.getNumCita() !=null && cita.getNumCita()>0){
                cita = citasFacade.edit(cita);
                this.addInfo("Se modificó la cita correctamente","Se modificó la cita correctamente");
            }else{
                cita = citasFacade.create(cita);
                this.addInfo("Se creó la cita correctamente","Se creó la cita correctamente");
            }
            
        String fecCita = new SimpleDateFormat("dd/MM/yyyy").format(cita.getFecCita());
        return "frmTblControlCitas.jsf?faces-redirect=true&fecCita="+fecCita+"&numCita="+cita.getNumCita(); 

        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
            this.addInfo("Error: No se guardo los Cambios","Error: No se guardo los Cambios");
            
        }
        return null;
    }
    
    public String regresar(){
        String fecCita = new SimpleDateFormat("dd/MM/yyyy").format(cita.getFecCita());
        return "frmTblControlCitas.jsf?faces-redirect=true&fecCita="+fecCita+"&numCita="+cita.getNumCita();
    }
}