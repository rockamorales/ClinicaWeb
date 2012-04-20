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
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoPago;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoPagoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatTipoPago")
@RequestScoped
public class FrmCatTipoPago extends PageBase {
    private CatTipoPago catTipoPago = new CatTipoPago();
    @EJB
    private CatTipoPagoFacadeLocal catTipoPagoFacade;
    
    private List<CatTipoPago> catTipoPagoList= new ArrayList<CatTipoPago>();

    public CatTipoPago getCatTipoPago() {
        return catTipoPago;
    }

    public void setCatTipoPago(CatTipoPago catTipoPago) {
        this.catTipoPago = catTipoPago;
    }

    public List<CatTipoPago> getCatTipoPagoList() {
        if (catTipoPagoList.isEmpty()){
          try{
            catTipoPagoList = catTipoPagoFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoPagoList;
    }

    public void setCatTipoPagoList(List<CatTipoPago> catTipoPagoList) {
        this.catTipoPagoList = catTipoPagoList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoPago = (CatTipoPago) table.getRowData();
            catTipoPagoFacade.remove(catTipoPago);
            catTipoPagoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
