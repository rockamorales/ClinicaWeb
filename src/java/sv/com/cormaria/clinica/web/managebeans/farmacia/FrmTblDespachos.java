/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.farmacia;

import sv.com.cormaria.clinica.web.managebeans.farmacia.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.farmacia.TblDespachos;
import sv.com.cormaria.servicios.facades.farmacia.TblDespachosFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmTblDespachos")
@ViewScoped
public class FrmTblDespachos extends PageBase {
    private TblDespachos cblDespacho = new TblDespachos();
    @EJB
    private TblDespachosFacadeLocal cblDespachoFacade;
    
    private List<TblDespachos> cblDespachoList= new ArrayList<TblDespachos>();

    public TblDespachos getTblDespachos() {
        return cblDespacho;
    }

    public void setTblDespachos(TblDespachos cblDespacho) {
        this.cblDespacho = cblDespacho;
    }

    public List<TblDespachos> getTblDespachosList() {
        if (cblDespachoList.isEmpty()){
          try{
            cblDespachoList = cblDespachoFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return cblDespachoList;
    }

    public void setTblDespachosList(List<TblDespachos> cblDespachoList) {
        this.cblDespachoList = cblDespachoList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            cblDespacho = (TblDespachos) table.getRowData();
            cblDespachoFacade.remove(cblDespacho);
            cblDespachoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
