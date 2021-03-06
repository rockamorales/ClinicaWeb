/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.criteria.ExpedientesSearchCriteria;
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
    private Integer numTarjeta = 0;
    
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
          if(tblExpediente.getTarjetaControlCitas()!=null){
            this.setNumTarjeta(tblExpediente.getTarjetaControlCitas().getNumTarjeta());
          }
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }
  
   public void seleccionar(ActionEvent ae){
    try{
        UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
        System.out.println("Buscando el expediente... "+((TblExpedientePacientes)table.getRowData()).getNumExpediente());
        this.tblExpediente = tblExpedienteFacade.find(((TblExpedientePacientes)table.getRowData()).getNumExpediente());
        if (tblExpediente.getTarjetaControlCitas()!=null){
            this.setNumTarjeta(tblExpediente.getTarjetaControlCitas().getNumTarjeta());
        }
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
   
   
      public boolean validar(){
       boolean isValid = true;
       
       if (this.getTblExpediente().getNumExpediente() == null || this.getTblExpediente().getNumExpediente() <= 0){
          isValid = false;
          this.addError("Por favor ingrese o seleccione el número de Expediente de paciente", "Por favor ingrese o seleccione el número de Expediente de paciente");
       } 
       
       if (this.getTblTarjeta().getNumTarjeta() == null || this.getTblTarjeta().getNumTarjeta() <= 0){
          isValid = false;
          this.addError("Por favor ingrese el número de tarjeta", "Por favor ingrese el número de tarjeta");
      }
       
       return isValid;
     
   }

    public Integer getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(Integer numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

   public void searchExpedienteByNumTarjeta(){
      try{
          ExpedientesSearchCriteria criteria = new ExpedientesSearchCriteria();
          criteria.setNumeroTarjeta(this.getNumTarjeta());
          List<TblExpedientePacientes> expedientes = tblExpedienteFacade.find(criteria, 0, 1);
          System.out.println("Lista de expedientes: "+expedientes.size());
          if (expedientes.size()>0){
              if(expedientes.get(0).getTarjetaControlCitas()!=null){
                  this.setNumTarjeta(expedientes.get(0).getTarjetaControlCitas().getNumTarjeta());
              }
              this.tblExpediente = expedientes.get(0);
          }else{
              this.tblExpediente = new TblExpedientePacientes();
          }
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }    

   
    public void asignar(ActionEvent ae){
        try{
             if (!validar()){
               return;
             }            
             tblTarjeta.setNumExpediente(tblExpediente.getNumExpediente());
             tblTarjetaFacadeLocal.create(this.tblTarjeta);
             this.addInfo("La tarjeta ha sido asignada", "La tarjeta ha sido asignada");
        }catch(Exception x){
            x.printStackTrace();
            //this.addError(x.getMessage(), x.getMessage());
            this.addError("El numero de tarjeta ya esta asignado", "El numero de tarjeta ya esta asignado");
        }
    }   
}
