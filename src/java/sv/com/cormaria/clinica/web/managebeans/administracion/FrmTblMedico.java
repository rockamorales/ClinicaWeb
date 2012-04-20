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
import javax.faces.event.ValueChangeEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmTblMedico")
@RequestScoped
public class FrmTblMedico extends PageBase {
    private TblMedico cblMedico = new TblMedico();
    @EJB
    private TblMedicoFacadeLocal cblMedicoFacade;
    
    private List<TblMedico> cblMedicoList= new ArrayList<TblMedico>();

    public TblMedico getTblMedico() {
        return cblMedico;
    }

    public void setTblMedico(TblMedico cblMedico) {
        this.cblMedico = cblMedico;
    }

    public List<TblMedico> getTblMedicoList() {
        if (cblMedicoList.isEmpty()){
          try{
            cblMedicoList = cblMedicoFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return cblMedicoList;
    }

    public void setTblMedicoList(List<TblMedico> cblMedicoList) {
        this.cblMedicoList = cblMedicoList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            cblMedico = (TblMedico) table.getRowData();
            cblMedicoFacade.remove(cblMedico);
            cblMedicoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void desactivar(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            cblMedico = (TblMedico) table.getRowData();            
            cblMedicoFacade.desactivar(cblMedico);
            cblMedicoList.clear();            
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    } 
}
