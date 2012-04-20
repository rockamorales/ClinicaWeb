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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoDonacion;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonacionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatTipoDonacion")
@RequestScoped
public class FrmCatTipoDonacion extends PageBase {
    private CatTipoDonacion catTipoDonacion = new CatTipoDonacion();
    @EJB
    private CatTipoDonacionFacadeLocal catTipoDonacionFacade;
    
    private List<CatTipoDonacion> catTipoDonacionList= new ArrayList<CatTipoDonacion>();

    public CatTipoDonacion getCatTipoDonacion() {
        return catTipoDonacion;
    }

    public void setCatTipoDonacion(CatTipoDonacion catTipoDonacion) {
        this.catTipoDonacion = catTipoDonacion;
    }

    public List<CatTipoDonacion> getCatTipoDonacionList() {
        if (catTipoDonacionList.isEmpty()){
          try{
            catTipoDonacionList = catTipoDonacionFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoDonacionList;
    }

    public void setCatTipoDonacionList(List<CatTipoDonacion> catTipoDonacionList) {
        this.catTipoDonacionList = catTipoDonacionList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoDonacion = (CatTipoDonacion) table.getRowData();
            catTipoDonacionFacade.remove(catTipoDonacion);
            catTipoDonacionList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
