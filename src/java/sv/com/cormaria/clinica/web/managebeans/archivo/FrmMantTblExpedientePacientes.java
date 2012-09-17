/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;


import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.NoSuchEntityException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.archivo.TblTarjetaControlCitas;
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.entidades.catalogos.CatSexo;
import sv.com.cormaria.servicios.entidades.catalogos.CatEstadoCivil;
import sv.com.cormaria.servicios.entidades.catalogos.CatOcupacion;
import sv.com.cormaria.servicios.entidades.catalogos.CatParentescoResponsable;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoConsulta;
import sv.com.cormaria.servicios.entidades.catalogos.CatUbicacionFisica;
import sv.com.cormaria.servicios.entidades.colecturia.TblComprobanteDonacion;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblTarjetaControlCitasFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatEstadoCivilFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatSexoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatOcupacionFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatParentescoResponsableFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoConsultaFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatUbicacionFisicaFacadeLocal;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;

/**
 *
 * @author Claudia
 */
@ManagedBean
@ViewScoped
public class FrmMantTblExpedientePacientes extends PageBase {
    private TblExpedientePacientes tblExpedientePacientes = new TblExpedientePacientes();

    @ManagedProperty(value="#{generarConsultaInf}")
    private GenerarConsultaInf generacionConsultaInf;

    /** Creates a new instance of FrmMantTblExpedientePacientes */
    public FrmMantTblExpedientePacientes() {
    }
    
    @EJB
    private TblExpedientePacientesFacadeLocal facade;   
    

    @EJB
    private CatSexoFacadeLocal catSexofacade;
    
    @EJB
    private CatOcupacionFacadeLocal catOcupacionfacade;
    
    @EJB
    private CatParentescoResponsableFacadeLocal catParentescoResponsablefacade;
    
    @EJB
    private CatEstadoCivilFacadeLocal catEstadoCivilfacade;
    
    @EJB
    private CatUbicacionFisicaFacadeLocal catUbicacionFisicafacade;
    
    @EJB
    private TblTarjetaControlCitasFacadeLocal tblTarjetaControlCitasfacade;
   
    private Integer numExpediente;
    private List<SelectItem> catSexoList = new ArrayList<SelectItem>();
    private List<SelectItem> catEstadoCivilList = new ArrayList<SelectItem>();
    private List<SelectItem> catOcupacionList = new ArrayList<SelectItem>();
    private List<SelectItem> catParentescoResponsableList = new ArrayList<SelectItem>();
    private List<SelectItem> catUbicacionFisicaList = new ArrayList<SelectItem>();
    private List<TblTarjetaControlCitas> tblTarjetaControlCitasList = new ArrayList<TblTarjetaControlCitas>();

    public GenerarConsultaInf getGeneracionConsultaInf() {
        return generacionConsultaInf;
    }

    public void setGeneracionConsultaInf(GenerarConsultaInf generacionConsultaInf) {
        this.generacionConsultaInf = generacionConsultaInf;
    }
    
    public List<TblTarjetaControlCitas> getTblTarjetaControlCitasList() {
        if(tblTarjetaControlCitasList.isEmpty() ){
            if(tblExpedientePacientes.getNumExpediente()!= null){
                try{
                    tblTarjetaControlCitasList = tblTarjetaControlCitasfacade.findByNumExpediente(tblExpedientePacientes.getNumExpediente());
                }
                catch(Exception ex){
                    ex.printStackTrace();

                }
            }
        }
        return tblTarjetaControlCitasList;
    }

    public void setTblTarjetaControlCitasList(List<TblTarjetaControlCitas> tblTarjetaControlCitasList) {
        this.tblTarjetaControlCitasList = tblTarjetaControlCitasList;
    }

    
    
    
    public List<SelectItem> getCatUbicacionFisicaList() {
        if(catUbicacionFisicaList.isEmpty()){
            try{
               List<CatUbicacionFisica> l =  catUbicacionFisicafacade.findActive();
               for (CatUbicacionFisica catUbicacionFisica : l) {
                   catUbicacionFisicaList.add(new SelectItem(catUbicacionFisica.getCodUbiFisica(), catUbicacionFisica.getEstUbiFisica()+"/"+catUbicacionFisica.getNivUbiFisica()+"/"+ catUbicacionFisica.getAniUbiFisica()));
               }
            }
           catch(Exception e){
               e.printStackTrace();
           }
        }
        return catUbicacionFisicaList;
    }

    public void setCatUbicacionFisicaList(List<SelectItem> catUbicacionFisicaList) {
        this.catUbicacionFisicaList = catUbicacionFisicaList;
    }
    
    public List<SelectItem> getCatParentescoResponsableList() {
        if(catParentescoResponsableList.isEmpty()){
            try{
               List<CatParentescoResponsable> l =  catParentescoResponsablefacade.findActive();
               for (CatParentescoResponsable catParentescoResponsable : l) {
                   catParentescoResponsableList.add(new SelectItem(catParentescoResponsable.getCodParResponsable(), catParentescoResponsable.getNomParResponsable()));
               }
            }
           catch(Exception e){
               e.printStackTrace();
           }
        }
        return catParentescoResponsableList;
    }

    public void setCatParentescoResponsableList(List<SelectItem> catParentescoResponsableList) {
        this.catParentescoResponsableList = catParentescoResponsableList;
    }

    public List<SelectItem> getCatOcupacionList() {
        if(catOcupacionList.isEmpty()){
            try{
               List<CatOcupacion> l =  catOcupacionfacade.findActive();
               for (CatOcupacion catOcupacion : l) {
                   catOcupacionList.add(new SelectItem(catOcupacion.getCodOcupacion(), catOcupacion.getNomOcupacion()));
               }
            }
           catch(Exception e){
               e.printStackTrace();
           }
        }
        return catOcupacionList;
    }

    public void setCatOcupacionList(List<SelectItem> catOcupacionList) {
        this.catOcupacionList = catOcupacionList;
    }

    public List<SelectItem> getCatEstadoCivilList() {
        if(catEstadoCivilList.isEmpty()){
            try{
               List<CatEstadoCivil> l =  catEstadoCivilfacade.findActive();
               for (CatEstadoCivil catEstadoCivil : l) {
                   catEstadoCivilList.add(new SelectItem(catEstadoCivil.getCodEstCivil(), catEstadoCivil.getNomEstCivil()));
               }
            }
           catch(Exception e){
               e.printStackTrace();
           }
        }
        return catEstadoCivilList;
    }

    public void setCatEstadoCivilList(List<SelectItem> catEstadoCivilList){
            this.catEstadoCivilList = catEstadoCivilList;
    }

    public List<SelectItem> getCatSexoList() {
        if(catSexoList.isEmpty()){
            try{
               List<CatSexo> l =  catSexofacade.findActive();
               for (CatSexo catSexo : l) {
                   catSexoList.add(new SelectItem(catSexo.getCodSexPaciente(), catSexo.getNomSexPaciente()));
               }
            }
           catch(Exception e){
               e.printStackTrace();
           }

        }
        return catSexoList;
    }

    public void setCatSexoList(List<SelectItem> catSexoList) {
        this.catSexoList = catSexoList;
    }

    public Integer getNumExpediente() {
        return numExpediente;
    }

    public void setNumExpediente(Integer numExpediente) {
        this.numExpediente = numExpediente;
    }

    public TblExpedientePacientes getTblExpedientePacientes() {
        return tblExpedientePacientes;
    }

    public void setTblExpedientePacientes(TblExpedientePacientes tblExpedientePacientes) {
        this.tblExpedientePacientes = tblExpedientePacientes;
    }
    
    public boolean validate(){
        boolean isValid = true;
        if (tblExpedientePacientes.getNumExpediente()==null || tblExpedientePacientes.getNumExpediente()<=0){
            this.addError("Por favor ingrese el numero de expediente", "Por favor ingrese el numero de expediente");
            isValid = false;
        }
        if (tblExpedientePacientes.getNomPaciente()==null || tblExpedientePacientes.getNomPaciente().trim().equals("")){
            this.addError("Por favor ingrese los nombres del paciente", "Por favor ingrese los nombres del paciente");
            isValid = false;
        }
        return isValid;
    }
      
    public void guardar(ActionEvent ae){
        try{
            if (!validate()){
                return;
            }
            if(tblExpedientePacientes.getNumExpediente() != null){
                facade.edit(tblExpedientePacientes);
            }else{
                facade.create(tblExpedientePacientes);
            }
            this.addInfo("La informacion ha sido guardada sin problemas", "La informacion ha sido guardada sin problemas");
        }catch(Exception x){
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblExpedientePacientes = new TblExpedientePacientes();
    }
    
    public void asignar(ActionEvent ae){
        try{
             //tblExpedientePacientes.getTblTarjetaControlCitas().setNumExpediente(tblExpedientePacientes.getTblExpedientePacientes().getNumExpediente());
             //tblTarjetaControlCitasfacade.create(tblExpedientePacientes.getTblTarjetaControlCitas());
             this.getTblTarjetaControlCitasList().clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void init(){
        if (this.getTblExpedientePacientes()==null || this.getTblExpedientePacientes().getNumExpediente()==null || this.getTblExpedientePacientes().getNumExpediente()<=0){
            if (numExpediente != null && numExpediente > 0){
                    System.out.println("Cargando el expediente...");
                    try{
                        this.tblExpedientePacientes = facade.find(numExpediente);
                    }catch(Exception ex){
                        ex.printStackTrace();
                        throw new RuntimeException(ex.getMessage());
                    }
            }
        }		
    }
    
  public void eliminar(ActionEvent ae){
      try{
          this.facade.remove(tblExpedientePacientes);
          tblExpedientePacientes = facade.find(this.tblExpedientePacientes.getNumExpediente());
          this.addInfo("El expediente ha sido desactivado", "El expediente ha sido desactivado");
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
  }

  public void searchExpedienteByNum(){
      try{
          this.tblExpedientePacientes = facade.find(this.tblExpedientePacientes.getNumExpediente());
          if (this.tblExpedientePacientes==null){
              this.addError("No se encontro informacion para el numero de expediente", "No se encontro informacion para el numero de expediente");
          }
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
  }
  
  public void seleccionar(ActionEvent ae){
    try{
        UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
        System.out.println("Buscando el expediente... "+((TblExpedientePacientes)table.getRowData()).getNumExpediente());
        this.tblExpedientePacientes = facade.find(((TblExpedientePacientes)table.getRowData()).getNumExpediente());
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
}
