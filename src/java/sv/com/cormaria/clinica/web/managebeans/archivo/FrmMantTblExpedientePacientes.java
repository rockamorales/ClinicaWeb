/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;


import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
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
    private CatTipoConsultaFacadeLocal tipoConsultaFacade;   
    
    @EJB
    private CatEspecialidadFacadeLocal especialidadFacade;   

    @EJB
    private TblMedicoFacadeLocal medicosFacade;

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
    private List<SelectItem> catTipoConsultaList = new ArrayList<SelectItem>();
    private List<SelectItem> catEspecialidadList = new ArrayList<SelectItem>();
    private List<SelectItem> tblMedicosList = new ArrayList<SelectItem>();
    private List<TblTarjetaControlCitas> tblTarjetaControlCitasList = new ArrayList<TblTarjetaControlCitas>();

    public GenerarConsultaInf getGeneracionConsultaInf() {
        return generacionConsultaInf;
    }

    public void setGeneracionConsultaInf(GenerarConsultaInf generacionConsultaInf) {
        this.generacionConsultaInf = generacionConsultaInf;
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

    public List<SelectItem> getTblMedicosList() {
        if (this.tblMedicosList.isEmpty()){
            try{
                List<TblMedico> l = medicosFacade.findAll();
                for (TblMedico medico : l) {
                    tblMedicosList.add(new SelectItem(medico.getNumMedico(), medico.getNomMedico()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tblMedicosList;
    }

    public void setTblMedicosList(List<SelectItem> tblMedicosList) {
        this.tblMedicosList = tblMedicosList;
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
      
    public void guardar(ActionEvent ae){
        try{
            if(tblExpedientePacientes.getNumExpediente() != null){
                facade.edit(tblExpedientePacientes);
            }else{
                facade.create(tblExpedientePacientes);
            }
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

    public void generarConsulta(ActionEvent ae){
      try{
           TblConsultas consultas = new TblConsultas();
           consultas.setCodTipConsulta(generacionConsultaInf.getCodTipConsulta());
           consultas.setNumExpediente(tblExpedientePacientes.getNumExpediente());
           consultas.setNumMedico(generacionConsultaInf.getNumMedico());
           consultas.setObsCliPaciente(generacionConsultaInf.getObservaciones());
           facade.generarConsulta(consultas, this.getTblExpedientePacientes(), generacionConsultaInf.getCodEspecialidad());
      }catch(Exception ex){
         ex.printStackTrace();
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
    

}
