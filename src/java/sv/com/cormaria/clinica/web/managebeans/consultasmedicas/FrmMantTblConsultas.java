/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.consultasmedicas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import org.richfaces.component.UIDataGrid;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ConsultasPagadasDataModel;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ConsultasSignosVitalesDataModel;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.entidades.catalogos.CatExamenesMedicos;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoReferencia;
import sv.com.cormaria.servicios.entidades.consultasmedicas.CatCategoriaExamenes;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblDetalleOrdenLaboratorio;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblDetalleOrdenLaboratorioPK;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblDetalleReceta;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblDetalleRecetaPK;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblOrdenLaboratorio;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblRecetaMedica;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblReferencia;
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.enums.EstadoRecetaMedica;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatCategoriaExamenesFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatExamenesMedicosFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoReferenciaFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblConsultasFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblDetalleOrdenLaboratorioFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblDetalleRecetaFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblOrdenLaboratorioFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblRecetaMedicaFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblReferenciaFacadeLocal;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmMantTblConsultas extends PageBase {

    
    private TblConsultas consulta = new TblConsultas();
    
    @EJB
    private TblConsultasFacadeLocal tblConsultasFacade;

    @EJB
    private TblRecetaMedicaFacadeLocal tblRecetaMedicaFacade;

    @EJB
    private TblDetalleRecetaFacadeLocal tblDetalleRecetaFacade;    

    @EJB
    private TblDetalleOrdenLaboratorioFacadeLocal tblDetalleOrdenLabFacade;

    @EJB
    private TblOrdenLaboratorioFacadeLocal tblOrdenLabFacade;
    
    @EJB
    private TblProductoFacadeLocal tblProductoFacade;

    @EJB
    private CatExamenesMedicosFacadeLocal catExamenesFacade;

    @EJB
    private CatCategoriaExamenesFacadeLocal catCategoriaExamenesFacade;

    @EJB
    private CatEspecialidadFacadeLocal catEspecialidadFacade;
    
    @EJB
    private CatTipoReferenciaFacadeLocal catTipoRefFacade;

    @EJB
    private TblReferenciaFacadeLocal tblReferenciaFacade;

    @EJB
    private TblMedicoFacadeLocal tblMedicoFacade;
    
    private TblRecetaMedica recetaMedica = new TblRecetaMedica();
    
    private List<TblDetalleReceta> detalleReceta = new ArrayList<TblDetalleReceta>();

    private List<TblMedico> tblMedicoList = new ArrayList<TblMedico>();

    private List<TblDetalleOrdenLaboratorio> detalleOrdenLabList = new ArrayList<TblDetalleOrdenLaboratorio>();
    private List<CatCategoriaExamenes> categoriaExamenesList = new ArrayList<CatCategoriaExamenes>();

    private TblDetalleOrdenLaboratorio detalleOrdenLab = new TblDetalleOrdenLaboratorio();

    private TblOrdenLaboratorio ordenLab = new TblOrdenLaboratorio();
    
    private TblDetalleReceta receta = new TblDetalleReceta();

    private TblReferencia referencia = new TblReferencia();
    
    private Map<Integer, Boolean> selectedExamenes = new HashMap<Integer, Boolean>();
    
    private List<TblProducto> productosList = new ArrayList<TblProducto>();
    private List<CatTipoReferencia> tiposReferenciasList = new ArrayList<CatTipoReferencia>();
    private List<CatEspecialidad> especialidadesList = new ArrayList<CatEspecialidad>();
    private List<CatExamenesMedicos> examenesList = new ArrayList<CatExamenesMedicos>();
    
    private Integer numConsulta;
    
    
    /**
     * Creates a new instance of FrmMantTblConsultas
     */
    public FrmMantTblConsultas() {
    }

    public Map<Integer, Boolean> getSelectedExamenes() {
        if (selectedExamenes.isEmpty()){
            for (TblDetalleOrdenLaboratorio detalleOrden : this.getDetalleOrdenLabList()) {
                selectedExamenes.put(detalleOrden.getTblDetalleOrdenLaboratorioPK().getCodExaMedico(), Boolean.TRUE);
            }
        }
        return selectedExamenes;
    }

    public void setSelectedExamenes(Map<Integer, Boolean> selectedExamenes) {
        this.selectedExamenes = selectedExamenes;
    }

    public List<CatCategoriaExamenes> getCategoriaExamenesList() {
        if (categoriaExamenesList.isEmpty()){
            try{
                categoriaExamenesList = catCategoriaExamenesFacade.findAll();
            }catch(Exception ex){
                
            }
        }
        return categoriaExamenesList;
    }

    public void setCategoriaExamenesList(List<CatCategoriaExamenes> categoriaExamenesList) {
        this.categoriaExamenesList = categoriaExamenesList;
    }

    public TblConsultas getConsulta() {
        return consulta;
    }

    public void setConsulta(TblConsultas consulta) {
        this.consulta = consulta;
    }

    public TblReferencia getReferencia() {
        return referencia;
    }

    public void setReferencia(TblReferencia referencia) {
        this.referencia = referencia;
    }

    public List<TblMedico> getTblMedicoList() {
        if (tblMedicoList.isEmpty()){
            try{
                tblMedicoList = tblMedicoFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tblMedicoList;
    }

    public void setTblMedicoList(List<TblMedico> tblMedicoList) {
        this.tblMedicoList = tblMedicoList;
    }
    
    public List<CatTipoReferencia> getTiposReferenciasList() {
        if (tiposReferenciasList.isEmpty()){
            try{
                tiposReferenciasList = catTipoRefFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tiposReferenciasList;
    }

    public void setTiposReferenciasList(List<CatTipoReferencia> tiposReferenciasList) {
        this.tiposReferenciasList = tiposReferenciasList;
    }

    public List<CatEspecialidad> getEspecialidadesList() {
        if (especialidadesList.isEmpty()){
            try{
                especialidadesList = catEspecialidadFacade.findActive();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return especialidadesList;
    }

    public void setEspecialidadesList(List<CatEspecialidad> especialidadesList) {
        this.especialidadesList = especialidadesList;
    }
    
    public List<CatExamenesMedicos> getExamenesList() {
        if (examenesList.isEmpty()){
            try{
                examenesList=catExamenesFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return examenesList;
    }

    public void setExamenesList(List<CatExamenesMedicos> examenesList) {
        this.examenesList = examenesList;
    }

    public List<TblDetalleOrdenLaboratorio> getDetalleOrdenLabList() {
        if (detalleOrdenLabList.isEmpty()){
            try{
                System.out.println(this.getOrdenLab().getNumOrdLaboratorio());
                if (this.getOrdenLab().getNumOrdLaboratorio()!=null && this.getOrdenLab().getNumOrdLaboratorio()>0){
                    detalleOrdenLabList = tblDetalleOrdenLabFacade.findByNumOrdenLaboratorio(this.getOrdenLab().getNumOrdLaboratorio());
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return detalleOrdenLabList;
    }

    public void setDetalleOrdenLabList(List<TblDetalleOrdenLaboratorio> detalleOrdenLabList) {
        this.detalleOrdenLabList = detalleOrdenLabList;
    }

    public TblDetalleOrdenLaboratorio getDetalleOrdenLab() {
        return detalleOrdenLab;
    }

    public void setDetalleOrdenLab(TblDetalleOrdenLaboratorio detalleOrdenLab) {
        this.detalleOrdenLab = detalleOrdenLab;
    }

    public TblOrdenLaboratorio getOrdenLab() {
        return ordenLab;
    }

    public void setOrdenLab(TblOrdenLaboratorio ordenLab) {
        this.ordenLab = ordenLab;
    }

    public List<TblProducto> getProductosList() {
        if (productosList.isEmpty()){
            try{
                productosList = tblProductoFacade.findMedicamentos();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return productosList;
    }

    public void setProductosList(List<TblProducto> productosList) {
        this.productosList = productosList;
    }

    public List<TblDetalleReceta> getDetalleReceta() {
        if( detalleReceta.isEmpty()){
            if (this.getRecetaMedica().getNumReceta()!=null && this.getRecetaMedica().getNumReceta()>0){
                try{
                    detalleReceta = tblDetalleRecetaFacade.findByNumReceta(this.getRecetaMedica().getNumReceta());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        return detalleReceta;
    }

    public void setDetalleReceta(List<TblDetalleReceta> detalleReceta) {
        this.detalleReceta = detalleReceta;
    }

    public TblDetalleReceta getReceta() {
        return receta;
    }

    public void setReceta(TblDetalleReceta receta) {
        this.receta = receta;
    }
    
    public TblRecetaMedica getRecetaMedica() {
        return recetaMedica;
    }

    public void setRecetaMedica(TblRecetaMedica recetaMedica) {
        this.recetaMedica = recetaMedica;
    }

    public Integer getNumConsulta() {
        return numConsulta;
    }

    public void setNumConsulta(Integer numConsulta) {
        this.numConsulta = numConsulta;
    }
    
   public boolean validar(){
       boolean isValid = true;
       
       if (this.getConsulta().getAnaPaciente() == ""){
          isValid = false;
          this.addError("Porfavor ingrese la anamnesis del paciente", "Porfavor ingrese la anamnesis del paciente");
       } 
       
       if (this.getConsulta().getExaFisPaciente() == ""){
           isValid = false;
           this.addError("Porfavor ingrese los resultados del examen fisico del paciente", "Porfavor ingrese los resultados del examen fisico del paciente");
       }
       
       if (this.getConsulta().getDiaPaciente() == ""){
           isValid = false;
           this.addError("Porfavor ingrese el diagnostico del paciente", "Porfavor ingrese el diagnostico del paciente");
       }
     /*  
       if (this.getReceta().getCanDetReceta() == 0 || this.getReceta().getCanDetReceta() <= 0){
           isValid = false;
           this.addError("Porfavor ingrese la cantidad", "Porfavor ingrese la cantidad");
       }       

       if (this.getReceta().getTblProducto().getNumProducto() == null || this.getReceta().getTblProducto().getNumProducto() == -1){
           isValid = false;
           this.addError("Porfavor seleccione el producto", "Porfavor seleccione el producto");
       } 
       
       if (this.getReceta().getDosDetReceta() == ""){
           isValid = false;
           this.addError("Porfavor ingrese la dosis del medicamento", "Porfavor ingrese la dosis del medicamento");
       }      
       
       if (this.getReceta().getFreDetReceta() == ""){
           isValid = false;
           this.addError("Porfavor ingrese la frecuencia del medicamento", "Porfavor ingrese la frecuencia del medicamento");
       }   

       if (this.getReceta().getDurTratamiento() == ""){
           isValid = false;
           this.addError("Porfavor ingrese la duración del medicamento", "Porfavor ingrese la duración del medicamento");
       } */        
       
       return isValid;
     
   }    
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }            
            consulta = tblConsultasFacade.edit(consulta);
            this.addInfo("La informacion ha sido guardada", "La informacion ha sido guardada");
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    
    public void init(){
        if (numConsulta != null && numConsulta >0){
            try{
                consulta = tblConsultasFacade.find(numConsulta);
                try{
                    referencia = tblReferenciaFacade.findByNumConsulta(consulta.getNumConsulta());
                    if (referencia == null){
                        referencia = new TblReferencia();
                    }
                }catch(Exception ex){
                    referencia = new TblReferencia();
                }
                try{
                    recetaMedica = tblRecetaMedicaFacade.findByNumConsulta(consulta.getNumConsulta());
                    if (recetaMedica == null){
                        recetaMedica = new TblRecetaMedica();
                    }
                }catch(Exception ex){
                    recetaMedica = new TblRecetaMedica();
                }
                try{
                    ordenLab = tblOrdenLabFacade.findByNumConsulta(consulta.getNumConsulta());
                    if (ordenLab == null){
                        ordenLab = new TblOrdenLaboratorio();
                    }
                }catch(Exception ex){
                    ordenLab = new TblOrdenLaboratorio();            
                }
                detalleReceta.clear();
                detalleOrdenLabList.clear();
                this.selectedExamenes.clear();
            }catch(Exception ex){
                ex.printStackTrace();
                this.addError(ex.getMessage(), ex.getMessage());
            }
        }
    }

    
   public void seleccionar(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            this.consulta = tblConsultasFacade.find(((TblConsultas)table.getRowData()).getNumConsulta());
            try{
                referencia = tblReferenciaFacade.findByNumConsulta(consulta.getNumConsulta());
                if (referencia == null){
                    referencia = new TblReferencia();
                }
            }catch(Exception ex){
                referencia = new TblReferencia();
            }
            try{
                recetaMedica = tblRecetaMedicaFacade.findByNumConsulta(consulta.getNumConsulta());
                if (recetaMedica == null){
                    recetaMedica = new TblRecetaMedica();
                }
            }catch(Exception ex){
                recetaMedica = new TblRecetaMedica();
            }
            try{
                ordenLab = tblOrdenLabFacade.findByNumConsulta(consulta.getNumConsulta());
                if (ordenLab == null){
                    ordenLab = new TblOrdenLaboratorio();
                }
            }catch(Exception ex){
                ordenLab = new TblOrdenLaboratorio();            
            }
            detalleReceta.clear();
            detalleOrdenLabList.clear();
            this.selectedExamenes.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
   }

   public void seleccionarFromDataGrid(ActionEvent ae){
    try{
        UIDataGrid table = (UIDataGrid) ae.getComponent().getParent();
        this.consulta = tblConsultasFacade.find(((TblConsultas)table.getRowData()).getNumConsulta());
        try{
            referencia = tblReferenciaFacade.findByNumConsulta(consulta.getNumConsulta());
            if (referencia == null){
                referencia = new TblReferencia();
            }
        }catch(Exception ex){
            referencia = new TblReferencia();
        }
        try{
            recetaMedica = tblRecetaMedicaFacade.findByNumConsulta(consulta.getNumConsulta());
            if (recetaMedica == null){
                recetaMedica = new TblRecetaMedica();
            }
        }catch(Exception ex){
            recetaMedica = new TblRecetaMedica();
        }
        try{
            ordenLab = tblOrdenLabFacade.findByNumConsulta(consulta.getNumConsulta());
            if (ordenLab == null){
                ordenLab = new TblOrdenLaboratorio();
            }
        }catch(Exception ex){
            ordenLab = new TblOrdenLaboratorio();            
        }
        detalleReceta.clear();
        detalleOrdenLabList.clear();
        this.selectedExamenes.clear();
    }catch(Exception x){
        x.printStackTrace();
        this.addError(x.getMessage(), x.getMessage());
    }
   }
   
   public void agregarDetalleReceta(ActionEvent ae){
       try{          
           TblDetalleRecetaPK pk = receta.getTblDetalleRecetaPK();
           pk.setNumReceta(recetaMedica.getNumReceta());
           tblDetalleRecetaFacade.create(receta);
           this.addInfo("La informacion ha sido guardada", "La informacion ha sido guardada");
           this.detalleReceta.clear();
       }catch(Exception ex){
           ex.printStackTrace();
          this.addError(ex.getMessage(), ex.getMessage());
       }
   }

   public void agregarDetalleOrdenLab(ActionEvent ae){
       try{
           TblDetalleOrdenLaboratorioPK pk = detalleOrdenLab.getTblDetalleOrdenLaboratorioPK();
           pk.setNumOrdLaboratorio(ordenLab.getNumOrdLaboratorio());
           List<Integer> examenes = new ArrayList<Integer>();
           for (Integer exam : selectedExamenes.keySet()) {
               if (selectedExamenes.get(exam)){
                   examenes.add(exam);
               }
           }
           tblDetalleOrdenLabFacade.addDetalleOrdenLab(examenes, this.getOrdenLab().getNumOrdLaboratorio());
           this.addInfo("La informacion ha sido guardada", "La informacion ha sido guardada");
           this.selectedExamenes.clear();
           this.detalleOrdenLabList.clear();
       }catch(Exception ex){
           ex.printStackTrace();
          this.addError(ex.getMessage(), ex.getMessage());
       }
   }
   
   
   public void guardarReceta(ActionEvent ae){
      try{
          if (recetaMedica.getNumReceta()!=null && recetaMedica.getNumReceta()>0){
            recetaMedica = tblRecetaMedicaFacade.edit(recetaMedica);
          }else{
            recetaMedica.setEstReceta(EstadoRecetaMedica.CREADA);
            recetaMedica.setNumConsulta(this.consulta.getNumConsulta());
            recetaMedica.setNumExpediente(this.consulta.getNumExpediente());
            recetaMedica.setNumMedico(this.consulta.getNumMedico());
            recetaMedica = tblRecetaMedicaFacade.create(recetaMedica);
          }
          this.addInfo("La informacion ha sido guardada", "La informacion ha sido guardada");
      }catch(Exception ex){
          ex.printStackTrace();
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }

   
      public boolean validar2(){
       boolean isValid2 = true;
       
       if (this.getOrdenLab().getDiaLaboratorio() == ""){
          isValid2 = false;
          this.addError("Porfavor ingrese el diagnostico del paciente", "Porfavor ingrese el diagnostico del paciente");
       } 
       
       if (this.getOrdenLab().getIndLaboratorio() == ""){
           isValid2 = false;
           this.addError("Porfavor ingrese las indicaciones para el paciente", "Porfavor ingrese las indicaciones para el paciente");
       }
       
       return isValid2;
     
   }
   
   public void guardarOrdenLab(ActionEvent ae){
      try{
           if (!validar2()){
               return;
           }          
          if (ordenLab.getNumOrdLaboratorio()!=null && ordenLab.getNumOrdLaboratorio()>0){
            ordenLab = tblOrdenLabFacade.edit(ordenLab);
          }else{
            ordenLab.setNumConsulta(this.consulta.getNumConsulta());
            ordenLab.setNumExpediente(this.consulta.getNumExpediente());
            ordenLab.setNumMedico(this.consulta.getNumMedico());
            ordenLab = tblOrdenLabFacade.create(ordenLab);
          }
          this.addInfo("La informacion ha sido guardada", "La informacion ha sido guardada");
      }catch(Exception ex){
          ex.printStackTrace();
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }

      public boolean validar3(){
       boolean isValid2 = true;
       
       if (this.getReferencia().getDiaReferencia() == ""){
          isValid2 = false;
          this.addError("Porfavor ingrese el diagnostico de la referencia", "Porfavor ingrese el diagnostico de la referencia");
       } 
       
       if (this.getReferencia().getMotReferencia() == ""){
           isValid2 = false;
           this.addError("Porfavor ingrese el motivo de la referencia", "Porfavor ingrese el motivo de la referencia");
       }
       
       if (this.getReferencia().getCodTipReferencia() == null || this.getReferencia().getCodTipReferencia() <= -1){
           isValid2 = false;
           this.addError("Porfavor seleccione el tipo de referencia", "Porfavor seleccione el tipo de referencia");
       }

       if (this.getReferencia().getCodEspecialidad() == null || this.getReferencia().getCodEspecialidad() <= -1){
           isValid2 = false;
           this.addError("Porfavor seleccione el tipo de especialiad", "Porfavor seleccione el tipo de especialidad");
       }  
       
       if (this.getReferencia().getRefA() == ""){
           isValid2 = false;
           this.addError("Porfavor ingrese el nombre del medico al que se realiza la referencia", "Porfavor ingrese el nombre del medico al que se realiza la referencia");
       }   
       
       return isValid2;
     
   }   
   
   public void guardarReferencia(ActionEvent ae){
      try{
           if (!validar3()){
               return;
           }           
          if (referencia.getNumReferencia()!=null && referencia.getNumReferencia()>0){
            referencia = tblReferenciaFacade.edit(referencia);
          }else{
            referencia.setNumConsulta(this.consulta.getNumConsulta());
            referencia.setNumExpediente(this.consulta.getNumExpediente());
            referencia.setNumMedico(this.consulta.getNumMedico());
            if (this.getSessionBean().getUsuario()!=null){
                referencia.setNumEmpleado(this.getSessionBean().getUsuario().getNumEmpleado());
            }
            referencia = tblReferenciaFacade.create(referencia);
          }
          this.addInfo("La informacion ha sido guardada", "La informacion ha sido guardada");
      }catch(Exception ex){
          ex.printStackTrace();
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }
   
   public void buscar(ActionEvent ae){
      ExpedienteDataModel model = (ExpedienteDataModel) this.getBean("#{expedienteDataModel}", ExpedienteDataModel.class);
      model.clear();
   }    
    
    public void searchExpedienteByNum(){
      try{
          this.consulta = tblConsultasFacade.find(this.consulta.getNumConsulta());
          if (this.consulta == null){
              this.addInfo("No se encontro ninguna consulta con el numero especificado", "No se encontro ninguna consulta con el numero especificado");
          }
        try{
            referencia = tblReferenciaFacade.findByNumConsulta(consulta.getNumConsulta());
            if (referencia == null){
                referencia = new TblReferencia();
            }
        }catch(Exception ex){
            referencia = new TblReferencia();
        }
        try{
            recetaMedica = tblRecetaMedicaFacade.findByNumConsulta(consulta.getNumConsulta());
            if (recetaMedica == null){
                recetaMedica = new TblRecetaMedica();
            }
        }catch(Exception ex){
            recetaMedica = new TblRecetaMedica();
        }
        try{
            ordenLab = tblOrdenLabFacade.findByNumConsulta(consulta.getNumConsulta());
            if (ordenLab == null){
                ordenLab = new TblOrdenLaboratorio();
            }
        }catch(Exception ex){
            ordenLab = new TblOrdenLaboratorio();            
        }
        detalleReceta.clear();
        detalleOrdenLabList.clear();
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }
    
    public void clear(ActionEvent ae){
        ConsultasSignosVitalesDataModel dataModel = (ConsultasSignosVitalesDataModel) this.getBean("#{consultasSignosVitalesDataModel}", ConsultasSignosVitalesDataModel.class);
        dataModel.clear();
    } 
    
    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            receta = (TblDetalleReceta) table.getRowData();
            tblDetalleRecetaFacade.remove(receta);
            this.detalleReceta.clear();
            this.addInfo("Se ha eliminado el medicamento de la receta", "Se ha eliminado el medicamento de la receta");
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }

    public void deleteDetalleOrdenLab(ActionEvent ae){
        try{
            UIDataGrid table = (UIDataGrid) ae.getComponent().getParent();
            detalleOrdenLab = (TblDetalleOrdenLaboratorio) table.getRowData();
            tblDetalleOrdenLabFacade.remove(detalleOrdenLab);
            this.detalleOrdenLabList.clear();
            this.selectedExamenes.clear();
            this.addInfo("Se ha eliminado el examen de la orden de laboratorio", "Se ha eliminado el examen de la orden de laboratorio");
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void finalizarConsulta(ActionEvent ae){
        try{
            tblConsultasFacade.finalizarConsulta(consulta.getNumConsulta());
            this.detalleOrdenLabList.clear();            
            this.detalleReceta.clear();
            consulta = new TblConsultas();
            referencia = new TblReferencia();
            recetaMedica = new TblRecetaMedica();
            ordenLab = new TblOrdenLaboratorio();            
            detalleReceta.clear();
            detalleOrdenLabList.clear();
            ConsultasSignosVitalesDataModel dataModel = (ConsultasSignosVitalesDataModel) this.getBean("#{consultasSignosVitalesDataModel}", ConsultasSignosVitalesDataModel.class);
            dataModel.clear();
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
}