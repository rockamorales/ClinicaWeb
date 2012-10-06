/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;

/**
 *
 * @author Claudia
 */
@ManagedBean (name = "frmTblExpedientePacientes")
@RequestScoped
public class FrmTblExpedientePacientes extends PageBase {
    private TblExpedientePacientes tblExpedientePacientes = new TblExpedientePacientes();
    @EJB
    private TblExpedientePacientesFacadeLocal tblExpedientePacientesFacade;
    
    private List<TblExpedientePacientes> tblExpedientePacientesList= new ArrayList<TblExpedientePacientes>();

    public TblExpedientePacientes getTblExpedientePacientes() {
        return tblExpedientePacientes;
    }

    public void setTblExpedientePacientes(TblExpedientePacientes tblExpedientePacientes) {
        this.tblExpedientePacientes = tblExpedientePacientes;
    }

    public List<TblExpedientePacientes> getTblExpedientePacientesList() {
        if (tblExpedientePacientesList.isEmpty()){
          try{
            tblExpedientePacientesList = tblExpedientePacientesFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return tblExpedientePacientesList;
    }

    public void setTblExpedientePacientesList(List<TblExpedientePacientes> tblExpedientePacientesList) {
        this.tblExpedientePacientesList = tblExpedientePacientesList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblExpedientePacientes = (TblExpedientePacientes) table.getRowData();
            tblExpedientePacientesFacade.remove(tblExpedientePacientes);
            tblExpedientePacientesList.clear();
            this.addInfo("El expediente ha sido desactivado", "El expediente ha sido desactivado");
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
