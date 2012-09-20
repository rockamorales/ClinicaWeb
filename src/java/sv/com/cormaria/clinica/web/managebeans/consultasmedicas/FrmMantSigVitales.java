package sv.com.cormaria.clinica.web.managebeans.consultasmedicas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataGrid;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.administracion.TblMovimientosExpediente;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblConsultasFacadeLocal;

/**
 *
 * @author GERARDO
 */
@ManagedBean
@ViewScoped
public class FrmMantSigVitales extends PageBase{

    /** Creates a new instance of FrmMantSigVitales */
    public FrmMantSigVitales() {
    }
    private TblConsultas tblConsulta = new TblConsultas();
    
    @EJB
    private TblConsultasFacadeLocal facade;
    private Integer numConsulta;

    public Integer getNumConsultas() {
        return numConsulta;
    }

    public void setNumConsultas(Integer numConsulta) {
        this.numConsulta = numConsulta;
    }
    
    public TblConsultas getTblConsulta() {
        return tblConsulta;
    }

    public void setTblConsulta(TblConsultas tblConsulta) {
        this.tblConsulta = tblConsulta;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(tblConsulta.getNumConsulta() != null){
                facade.edit(tblConsulta);
            }else{
                facade.create(tblConsulta);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
   public void seleccionar(ActionEvent ae){
    try{
        UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
        this.tblConsulta = facade.find(((TblConsultas)table.getRowData()).getNumConsulta());
    }catch(Exception x){
        x.printStackTrace();
        this.addError(x.getMessage(), x.getMessage());
    }
   }

   public void seleccionarFromDataGrid(ActionEvent ae){
    try{
        UIDataGrid table = (UIDataGrid) ae.getComponent().getParent().getParent();
        this.tblConsulta = facade.find(((TblConsultas)table.getRowData()).getNumConsulta());
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
       this.tblConsulta = new TblConsultas();
   }
    
    
    public void searchExpedienteByNum(){
      try{
          this.tblConsulta = facade.find(this.tblConsulta.getNumConsulta());
          if (this.tblConsulta == null){
              this.addInfo("No se encontro ninguna consulta con el numero especificado", "No se encontro ninguna consulta con el numero especificado");
          }
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }
    
   
}
