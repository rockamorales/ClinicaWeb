/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.archivo.TblTarjetaControlCitas;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblTarjetaControlCitasFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmCrearTarjetaControlCitas extends PageBase{

    @EJB
    private TblExpedientePacientesFacadeLocal tblExpedienteFacade;

    @EJB
    private TblTarjetaControlCitasFacadeLocal tblTarjetaFacadeLocal;
        
    private TblExpedientePacientes tblExpediente = new TblExpedientePacientes();

    private TblTarjetaControlCitas tblTarjeta = new TblTarjetaControlCitas();
    
    /**
     * Creates a new instance of FrmGenerarConsulta
     */
    public FrmCrearTarjetaControlCitas() {
    }

    public TblTarjetaControlCitas getTblTarjeta() {
        return tblTarjeta;
    }

    public void setTblTarjeta(TblTarjetaControlCitas tblTarjeta) {
        this.tblTarjeta = tblTarjeta;
    }

    public TblExpedientePacientes getTblExpediente() {
        return tblExpediente;
    }

    public void setTblExpediente(TblExpedientePacientes tblExpediente) {
        this.tblExpediente = tblExpediente;
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
   
   public void asignarNuevo(ActionEvent ae){
       this.tblExpediente = new TblExpedientePacientes();
       this.tblTarjeta = new TblTarjetaControlCitas();
   }
   
    public void asignar(ActionEvent ae){
        try{
             tblTarjeta.setNumExpediente(tblExpediente.getNumExpediente());
             tblTarjetaFacadeLocal.create(this.tblTarjeta);
             this.addInfo("La tarjeta ha sido asignada", "La tarjeta ha sido asignada");
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }   
}
