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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoProducto;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoProductoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatTipoProducto")
@RequestScoped
public class FrmCatTipoProducto extends PageBase {
    private CatTipoProducto catTipoProducto = new CatTipoProducto();
    @EJB
    private CatTipoProductoFacadeLocal catTipoProductoFacade;
    
    private List<CatTipoProducto> catTipoProductoList= new ArrayList<CatTipoProducto>();

    public CatTipoProducto getCatTipoProducto() {
        return catTipoProducto;
    }

    public void setCatTipoProducto(CatTipoProducto catTipoProducto) {
        this.catTipoProducto = catTipoProducto;
    }

    public List<CatTipoProducto> getCatTipoProductoList() {
        if (catTipoProductoList.isEmpty()){
          try{
            catTipoProductoList = catTipoProductoFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoProductoList;
    }

    public void setCatTipoProductoList(List<CatTipoProducto> catTipoProductoList) {
        this.catTipoProductoList = catTipoProductoList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoProducto = (CatTipoProducto) table.getRowData();
            catTipoProductoFacade.remove(catTipoProducto);
            catTipoProductoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
