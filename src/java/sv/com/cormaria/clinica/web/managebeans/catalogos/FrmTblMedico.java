/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;

import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;


/**
 *
 * @author Schumy
 */
@ManagedBean
@ViewScoped
public class FrmTblMedico extends PageBase {
    private TblMedico tblMedico = new TblMedico();
    @EJB
    private TblMedicoFacadeLocal tblMedicoFacade;
    
    private List<TblMedico> tblMedicoList= new ArrayList<TblMedico>();

    public TblMedico getTblMedico() {
        return tblMedico;
    }

    public void setTblMedico(TblMedico tblMedico) {
        this.tblMedico = tblMedico;
    }

    public List<TblMedico> getTblMedicoList() {
        if (tblMedicoList.isEmpty()){
            try{
                tblMedicoList = tblMedicoFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tblMedicoList;
    }

    public void setTblMedicoList(List<TblMedico> tblMedicoList) {
        this.tblMedicoList = tblMedicoList;
    }




    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblMedico = (TblMedico) table.getRowData();
            tblMedicoFacade.remove(tblMedico);
            tblMedicoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
