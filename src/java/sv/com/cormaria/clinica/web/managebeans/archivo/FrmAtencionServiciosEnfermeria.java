/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataGrid;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ServiciosEnfermeriaPagadosDataModel;
import sv.com.cormaria.servicios.entidades.archivo.TblServiciosEnfermeria;
import sv.com.cormaria.servicios.facades.archivo.TblServiciosEnfermeriaFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmAtencionServiciosEnfermeria extends PageBase{
    
    @EJB
    private TblServiciosEnfermeriaFacadeLocal serviciosEnfermeriaFacade;

    private TblServiciosEnfermeria tblServicioEnfermeria = new TblServiciosEnfermeria();
    /**
     * Creates a new instance of FrmAtencionServiciosEnfermeria
     */
    public FrmAtencionServiciosEnfermeria() {
    }

    public TblServiciosEnfermeria getTblServicioEnfermeria() {
        return tblServicioEnfermeria;
    }

    public void setTblServicioEnfermeria(TblServiciosEnfermeria tblServicioEnfermeria) {
        this.tblServicioEnfermeria = tblServicioEnfermeria;
    }
    
  public void seleccionarFromDataGrid(ActionEvent ae){
    try{
        UIDataGrid table = (UIDataGrid) ae.getComponent().getParent();
        this.tblServicioEnfermeria = serviciosEnfermeriaFacade.find(((TblServiciosEnfermeria)table.getRowData()).getNumSerEnfermeria());
    }catch(Exception x){
        x.printStackTrace();
        this.addError(x.getMessage(), x.getMessage());
    }
   }
       
   public void clear(ActionEvent ae){
        ServiciosEnfermeriaPagadosDataModel dataModel = (ServiciosEnfermeriaPagadosDataModel) this.getBean("#{serviciosEnfermeriaPagadosDataModel}", ServiciosEnfermeriaPagadosDataModel.class);
        dataModel.clear();
   }
   
   public void guardar(ActionEvent ae){
       try{
           this.serviciosEnfermeriaFacade.saveAndMarkAsApplied(tblServicioEnfermeria);
           ServiciosEnfermeriaPagadosDataModel dataModel = (ServiciosEnfermeriaPagadosDataModel) this.getBean("#{serviciosEnfermeriaPagadosDataModel}", ServiciosEnfermeriaPagadosDataModel.class);
           dataModel.clear();
           this.addInfo("La informacion ha sido almacenada sin problemas", "La informacion ha sido almacenada sin problemas");
           
       }catch(Exception ex){
           ex.printStackTrace();
           this.addError(ex.getMessage(), ex.getMessage());
       }
               
   }
}