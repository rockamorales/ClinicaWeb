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
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.administracion.TblMovimientosExpediente;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.catalogos.CatAreas;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoTransaccion;
import sv.com.cormaria.servicios.facades.administracion.TblMovimientosExpedienteFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatAreasFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoTransaccionFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmTblMovimientoExpedientePacientes extends PageBase{

    /**
     * Creates a new instance of FrmTblMovimientoExpedientePacientes
     */    
    @EJB
    private TblExpedientePacientesFacadeLocal expediente;   

    @EJB
    private CatAreasFacadeLocal areasFacade;   

    @EJB
    private CatTipoTransaccionFacadeLocal tipTransaccionFacade;
    
    @EJB
    private TblMovimientosExpedienteFacadeLocal movimientoExpediente;

    private List<CatAreas> areasList = new ArrayList<CatAreas>();

    private List<CatTipoTransaccion> tipoTransaccionList = new ArrayList<CatTipoTransaccion>();
    
    private TblExpedientePacientes tblExpedientePacientes = new TblExpedientePacientes();

    private TblMovimientosExpediente tblMovimientosExpediente = new TblMovimientosExpediente();

    public TblExpedientePacientes getTblExpedientePacientes() {
        return tblExpedientePacientes;
    }

    public void setTblExpedientePacientes(TblExpedientePacientes tblExpedientePacientes) {
        this.tblExpedientePacientes = tblExpedientePacientes;
    }

    public TblMovimientosExpediente getTblMovimientosExpediente() {
        return tblMovimientosExpediente;
    }

    public void setTblMovimientosExpediente(TblMovimientosExpediente tblMovimientosExpediente) {
        this.tblMovimientosExpediente = tblMovimientosExpediente;
    }

    public List<CatAreas> getAreasList() {
        if (areasList.isEmpty()){
            try{
                areasList = areasFacade.findActive();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return areasList;
    }

    public void setAreasList(List<CatAreas> areasList) {
        this.areasList = areasList;
    }

    public List<CatTipoTransaccion> getTipoTransaccionList() {
        if (tipoTransaccionList.isEmpty()){
            try{
                tipoTransaccionList = tipTransaccionFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tipoTransaccionList;
    }

    public void setTipoTransaccionList(List<CatTipoTransaccion> tipoTransaccionList){
        this.tipoTransaccionList = tipoTransaccionList;
    }
    
    public void searchExpedienteByNum(){
      try{
          this.tblExpedientePacientes = expediente.find(this.tblExpedientePacientes.getNumExpediente());
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }
  
   public void seleccionar(ActionEvent ae){
    try{
        UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
        System.out.println("Buscando el expediente... "+((TblExpedientePacientes)table.getRowData()).getNumExpediente());
        this.tblExpedientePacientes = expediente.find(((TblExpedientePacientes)table.getRowData()).getNumExpediente());
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
   
   public void nuevoTraslado(ActionEvent ae){
       this.tblMovimientosExpediente = new TblMovimientosExpediente();
       this.tblExpedientePacientes = new TblExpedientePacientes();
   }
   
   public boolean validar(){
       boolean isValid = true;
       
       if (this.getTblExpedientePacientes().getNumExpediente() == null || this.getTblExpedientePacientes().getNumExpediente() <= 0){
          isValid = false;
          this.addError("Porfavor ingrese o seleccione el número de Expediente de paciente", "Por favor ingrese o seleccione el número de Expediente de paciente");
       }
       
       return isValid;
     
   }
   
   public void generarMovimiento(ActionEvent ae){
       try{
           if (!validar()){
               return;
           }
            if (tblMovimientosExpediente.getNumTransaccion()!=null && tblMovimientosExpediente.getNumTransaccion()>0){
                tblMovimientosExpediente.setNumExpediente(this.getTblExpedientePacientes().getNumExpediente());
                movimientoExpediente.edit(tblMovimientosExpediente);
            }else{
                if (this.getSessionBean().getUsuario()!=null && this.getSessionBean().getUsuario().getEmpleado()!=null){
                    tblMovimientosExpediente = movimientoExpediente.generarMovimiento(tblExpedientePacientes, tblMovimientosExpediente, this.getSessionBean().getUsuario().getEmpleado().getNumEmpleado());
                    this.addInfo("El traslado ha sido generado sin problemas", "El traslado ha sido generado sin problemas");
                }else{
                    this.addError("El usuario con el que ha ingresado a la aplicacion no tiene un codigo de empleado asociado", "El usuario con el que ha ingresado a la aplicacion no tiene un codigo de empleado asociado");
                }
            }
           
       }catch(Exception ex){
           ex.printStackTrace();
           this.addError(ex.getMessage(), ex.getMessage());
       }
   }
}
