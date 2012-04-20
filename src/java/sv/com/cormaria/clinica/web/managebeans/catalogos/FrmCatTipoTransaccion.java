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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoTransaccion;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoTransaccionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatTipoTransaccion")
@RequestScoped
public class FrmCatTipoTransaccion extends PageBase {
    private CatTipoTransaccion catTipoTransaccion = new CatTipoTransaccion();
    @EJB
    private CatTipoTransaccionFacadeLocal catTipoTransaccionFacade;
    
    private List<CatTipoTransaccion> catTipoTransaccionList= new ArrayList<CatTipoTransaccion>();

    public CatTipoTransaccion getCatTipoTransaccion() {
        return catTipoTransaccion;
    }

    public void setCatTipoTransaccion(CatTipoTransaccion catTipoTransaccion) {
        this.catTipoTransaccion = catTipoTransaccion;
    }

    public List<CatTipoTransaccion> getCatTipoTransaccionList() {
        if (catTipoTransaccionList.isEmpty()){
          try{
            catTipoTransaccionList = catTipoTransaccionFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoTransaccionList;
    }

    public void setCatTipoTransaccionList(List<CatTipoTransaccion> catTipoTransaccionList) {
        this.catTipoTransaccionList = catTipoTransaccionList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoTransaccion = (CatTipoTransaccion) table.getRowData();
            catTipoTransaccionFacade.remove(catTipoTransaccion);
            catTipoTransaccionList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
