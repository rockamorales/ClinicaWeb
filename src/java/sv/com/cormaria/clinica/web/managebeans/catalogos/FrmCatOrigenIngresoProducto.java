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
import sv.com.cormaria.servicios.entidades.catalogos.CatOrigenIngresoProducto;
import sv.com.cormaria.servicios.facades.catalogos.CatOrigenIngresoProductoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatOrigenIngresoProducto")
@RequestScoped
public class FrmCatOrigenIngresoProducto extends PageBase {
    private CatOrigenIngresoProducto catOrigenIngresoProducto = new CatOrigenIngresoProducto();
    @EJB
    private CatOrigenIngresoProductoFacadeLocal catOrigenIngresoProductoFacade;
    
    private List<CatOrigenIngresoProducto> catOrigenIngresoProductoList= new ArrayList<CatOrigenIngresoProducto>();

    public CatOrigenIngresoProducto getCatOrigenIngresoProducto() {
        return catOrigenIngresoProducto;
    }

    public void setCatOrigenIngresoProducto(CatOrigenIngresoProducto catOrigenIngresoProducto) {
        this.catOrigenIngresoProducto = catOrigenIngresoProducto;
    }

    public List<CatOrigenIngresoProducto> getCatOrigenIngresoProductoList() {
        if (catOrigenIngresoProductoList.isEmpty()){
          try{
            catOrigenIngresoProductoList = catOrigenIngresoProductoFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catOrigenIngresoProductoList;
    }

    public void setCatOrigenIngresoProductoList(List<CatOrigenIngresoProducto> catOrigenIngresoProductoList) {
        this.catOrigenIngresoProductoList = catOrigenIngresoProductoList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catOrigenIngresoProducto = (CatOrigenIngresoProducto) table.getRowData();
            catOrigenIngresoProductoFacade.remove(catOrigenIngresoProducto);
            catOrigenIngresoProductoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
