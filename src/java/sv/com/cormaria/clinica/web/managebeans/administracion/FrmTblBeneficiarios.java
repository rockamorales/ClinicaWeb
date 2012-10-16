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
import sv.com.cormaria.servicios.entidades.administracion.TblBeneficiarios;
import sv.com.cormaria.servicios.facades.administracion.TblBeneficiariosFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmTblBeneficiarios")
@RequestScoped
public class FrmTblBeneficiarios extends PageBase {
    private TblBeneficiarios tblBeneficiarios = new TblBeneficiarios();
    @EJB
    private TblBeneficiariosFacadeLocal tblBeneficiariosFacade;
    
    private List<TblBeneficiarios> tblBeneficiariosList= new ArrayList<TblBeneficiarios>();

    public TblBeneficiarios getTblBeneficiarios() {
        return tblBeneficiarios;
    }

    public void setTblBeneficiarios(TblBeneficiarios tblBeneficiarios) {
        this.tblBeneficiarios = tblBeneficiarios;
    }

    public List<TblBeneficiarios> getTblBeneficiariosList() {
        if (tblBeneficiariosList.isEmpty()){
          try{
            tblBeneficiariosList = tblBeneficiariosFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return tblBeneficiariosList;
    }

    public void setTblBeneficiariosList(List<TblBeneficiarios> tblBeneficiariosList) {
        this.tblBeneficiariosList = tblBeneficiariosList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblBeneficiarios = (TblBeneficiarios) table.getRowData();
            tblBeneficiariosFacade.remove(tblBeneficiarios);
            tblBeneficiariosList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void desactivar(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblBeneficiarios = (TblBeneficiarios) table.getRowData();            
            tblBeneficiariosFacade.desactivar(tblBeneficiarios.getNumBeneficiario());
            tblBeneficiariosList.clear();            
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    } 
}
