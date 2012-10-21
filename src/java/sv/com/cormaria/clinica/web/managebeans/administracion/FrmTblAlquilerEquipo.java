/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.administracion;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.AlquileresDataModel;
import sv.com.cormaria.clinica.web.managebeans.datamodels.RequisicionesDataModel;
import sv.com.cormaria.servicios.criteria.RequisicionesSearchCriteria;
import sv.com.cormaria.servicios.entidades.administracion.TblRequisiciones;
import sv.com.cormaria.servicios.entidades.farmacia.TblAlquilerEquipo;
import sv.com.cormaria.servicios.facades.administracion.TblRequisicionesFacadeLocal;
import sv.com.cormaria.servicios.facades.farmacia.TblAlquilerEquipoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean
@ViewScoped
public class FrmTblAlquilerEquipo extends PageBase {
    private TblAlquilerEquipo tblAlquiler = new TblAlquilerEquipo();

    @EJB
    private TblAlquilerEquipoFacadeLocal tblAlquilerEquipoFacade;
    
    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblAlquiler = (TblAlquilerEquipo) table.getRowData();
            tblAlquilerEquipoFacade.remove(tblAlquiler);
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void buscar(ActionEvent ae){
        AlquileresDataModel dataModel = (AlquileresDataModel) this.getBean("#{alquileresDataModel}", AlquileresDataModel.class);
        dataModel.clear();
    }
}
