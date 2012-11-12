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
/*import sv.com.cormaria.clinica.web.managebeans.datamodels.IngresosProductoEmitidosDataModel;*/
import sv.com.cormaria.servicios.entidades.farmacia.TblIngresosProducto;
import sv.com.cormaria.servicios.facades.farmacia.TblIngresosProductoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmTblIngresosProducto")
@ViewScoped
public class FrmTblIngresosProducto extends PageBase {
    private TblIngresosProducto cblIngresoProducto = new TblIngresosProducto();
    @EJB
    private TblIngresosProductoFacadeLocal cblIngresoProductoFacade;
    
    private List<TblIngresosProducto> cblIngresoProductoList= new ArrayList<TblIngresosProducto>();

    public TblIngresosProducto getTblIngresosProducto() {
        return cblIngresoProducto;
    }

    public void setTblIngresosProducto(TblIngresosProducto cblIngresoProducto) {
        this.cblIngresoProducto = cblIngresoProducto;
    }

    public List<TblIngresosProducto> getTblIngresosProductoList() {
        if (cblIngresoProductoList.isEmpty()){
          try{
            cblIngresoProductoList = cblIngresoProductoFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return cblIngresoProductoList;
    }

    public void setTblIngresosProductoList(List<TblIngresosProducto> cblIngresoProductoList) {
        this.cblIngresoProductoList = cblIngresoProductoList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            cblIngresoProducto = (TblIngresosProducto) table.getRowData();
            cblIngresoProductoFacade.remove(cblIngresoProducto);
            cblIngresoProductoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    /*public void buscar(ActionEvent ae){
        IngresoProductoEmitidosDataModel dataModel = (IngresoProductoEmitidosDataModel) this.getBean("#{comprobanteDonacionEmitidosDataModel}", IngresoProductoEmitidosDataModel.class);
        dataModel.clear();
    }*/
}

