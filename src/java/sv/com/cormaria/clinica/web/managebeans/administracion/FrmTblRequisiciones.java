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
import sv.com.cormaria.clinica.web.managebeans.datamodels.RequisicionesDataModel;
import sv.com.cormaria.servicios.criteria.RequisicionesSearchCriteria;
import sv.com.cormaria.servicios.entidades.administracion.TblRequisiciones;
import sv.com.cormaria.servicios.facades.administracion.TblRequisicionesFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean(name = "frmTblRequisiciones")
@ViewScoped
public class FrmTblRequisiciones extends PageBase {
    private TblRequisiciones tblRequisiciones = new TblRequisiciones();

    @EJB
    private TblRequisicionesFacadeLocal tblRequisicionesFacade;
    
    private List<TblRequisiciones> tblRequisicionesList= new ArrayList<TblRequisiciones>();

    public TblRequisiciones getTblRequisiciones() {
        return tblRequisiciones;
    }

    public void setTblRequisiciones(TblRequisiciones tblRequisiciones) {
        this.tblRequisiciones = tblRequisiciones;
    }

    public List<TblRequisiciones> getTblRequisicionesList() {
        if (tblRequisicionesList.isEmpty()){
          try{
            tblRequisicionesList = tblRequisicionesFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return tblRequisicionesList;
    }

    public void setTblRequisicionesList(List<TblRequisiciones> tblRequisicionesList) {
        this.tblRequisicionesList = tblRequisicionesList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblRequisiciones = (TblRequisiciones) table.getRowData();
            tblRequisicionesFacade.remove(tblRequisiciones);
            tblRequisicionesList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void buscar(ActionEvent ae){
        RequisicionesDataModel dataModel = (RequisicionesDataModel) this.getBean("#{requisicionesDataModel}", RequisicionesDataModel.class);
        dataModel.clear();
    }
}
