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
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.archivo.TblServiciosEnfermeria;
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoConsulta;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoServiciosEnfermeria;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblServiciosEnfermeriaFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoConsultaFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServicioFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServiciosEnfermeriaFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblConsultasFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmTblCrearServiciosEnfermeria extends PageBase{

    @EJB
    private TblServiciosEnfermeriaFacadeLocal tblServiciosEnfermeriaFacade;

    @EJB
    private TblExpedientePacientesFacadeLocal tblExpedienteFacade;
        
    private TblExpedientePacientes tblExpediente = new TblExpedientePacientes();
        
    @EJB
    private CatTipoServiciosEnfermeriaFacadeLocal catTipoServicioFacade;

    private List<CatTipoServiciosEnfermeria> tipoServiciosList = new ArrayList<CatTipoServiciosEnfermeria>();
    
    private TblServiciosEnfermeria tblServiciosEnfermeria = new TblServiciosEnfermeria();

    public List<CatTipoServiciosEnfermeria> getTipoServiciosList() {
        if (tipoServiciosList.isEmpty()){
            try{
                tipoServiciosList = catTipoServicioFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tipoServiciosList;
    }

    public void setTipoServiciosList(List<CatTipoServiciosEnfermeria> tipoServiciosList) {
        this.tipoServiciosList = tipoServiciosList;
    }

    public TblExpedientePacientes getTblExpediente() {
        return tblExpediente;
    }

    public void setTblExpediente(TblExpedientePacientes tblExpediente) {
        this.tblExpediente = tblExpediente;
    }

    public TblServiciosEnfermeria getTblServiciosEnfermeria() {
        return tblServiciosEnfermeria;
    }

    public void setTblServiciosEnfermeria(TblServiciosEnfermeria tblServiciosEnfermeria) {
        this.tblServiciosEnfermeria = tblServiciosEnfermeria;
    }
    
    public void crearServiciosEnfermeria(ActionEvent ae){
      try{
          if (tblServiciosEnfermeria.getNumSerEnfermeria()!=null && tblServiciosEnfermeria.getNumSerEnfermeria() > 0){
              //Si se actualiza la consulta, se tiene que considerar actualizar el comprobante de 
              //donacion asociado.
          }else{
            tblServiciosEnfermeria.setNumSerEnfermeria(null);
            tblServiciosEnfermeria.setNumExpediente(tblExpediente.getNumExpediente());
            tblServiciosEnfermeria = tblServiciosEnfermeriaFacade.crearServicio(tblServiciosEnfermeria, this.getTblExpediente());
            this.addInfo("El servicio ha sido creado", "El servicio ha sido creado");
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
   
   public void nuevoServicio(ActionEvent ae){
       this.tblExpediente = new TblExpedientePacientes();
       this.tblServiciosEnfermeria = new TblServiciosEnfermeria();
   }
}
