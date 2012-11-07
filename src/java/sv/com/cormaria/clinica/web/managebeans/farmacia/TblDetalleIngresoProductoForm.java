/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.farmacia;

import sv.com.cormaria.clinica.web.managebeans.farmacia.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.farmacia.TblDetalleIngresoProducto;

/**
 *
 * @author Mackk
 */
@ManagedBean
@ViewScoped
public class TblDetalleIngresoProductoForm {
private TblDetalleIngresoProducto tblDetalleIngresoProducto = new TblDetalleIngresoProducto();

    public TblDetalleIngresoProducto getTblDetalleIngresoProducto() {
        return tblDetalleIngresoProducto;
    }

    public void setTblDetalleIngresoProducto(TblDetalleIngresoProducto tblDetalleIngresoProducto) {
        this.tblDetalleIngresoProducto = tblDetalleIngresoProducto;
    }


    /** Creates a new instance of TblDetalleIngresoProductoForm */
    public TblDetalleIngresoProductoForm() {
    }
}
