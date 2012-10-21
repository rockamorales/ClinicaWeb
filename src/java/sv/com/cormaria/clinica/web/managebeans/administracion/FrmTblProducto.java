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
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmTblProducto")
@RequestScoped
public class FrmTblProducto extends PageBase {
    private TblProducto tblProducto = new TblProducto();
    @EJB
    private TblProductoFacadeLocal tblProductoFacade;
    
    private List<TblProducto> tblProductoList= new ArrayList<TblProducto>();

    public TblProducto getTblProducto() {
        return tblProducto;
    }

    public void setTblProducto(TblProducto tblProducto) {
        this.tblProducto = tblProducto;
    }

    public List<TblProducto> getTblProductoList() {
        if (tblProductoList.isEmpty()){
          try{
            tblProductoList = tblProductoFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return tblProductoList;
    }

    public void setTblProductoList(List<TblProducto> tblProductoList) {
        this.tblProductoList = tblProductoList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblProducto = (TblProducto) table.getRowData();
            tblProductoFacade.remove(tblProducto);
            tblProductoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void desactivar(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblProducto = (TblProducto) table.getRowData();            
            tblProductoFacade.desactivar(tblProducto.getNumProducto());
            tblProductoList.clear();            
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    } 
}
