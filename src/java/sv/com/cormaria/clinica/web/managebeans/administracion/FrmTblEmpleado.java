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
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblEmpleado;
import sv.com.cormaria.servicios.facades.administracion.TblEmpleadoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmTblEmpleado")
@RequestScoped
public class FrmTblEmpleado extends PageBase {
    private TblEmpleado tblEmpleado = new TblEmpleado();
    @EJB
    private TblEmpleadoFacadeLocal tblEmpleadoFacade;
    
    private List<TblEmpleado> tblEmpleadoList= new ArrayList<TblEmpleado>();

    public TblEmpleado getTblEmpleado() {
        return tblEmpleado;
    }

    public void setTblEmpleado(TblEmpleado tblEmpleado) {
        this.tblEmpleado = tblEmpleado;
    }

    public List<TblEmpleado> getTblEmpleadoList() {
        if (tblEmpleadoList.isEmpty()){
          try{
            tblEmpleadoList = tblEmpleadoFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return tblEmpleadoList;
    }

    public void setTblEmpleadoList(List<TblEmpleado> tblEmpleadoList) {
        this.tblEmpleadoList = tblEmpleadoList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblEmpleado = (TblEmpleado) table.getRowData();
            tblEmpleadoFacade.remove(tblEmpleado);
            tblEmpleadoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
