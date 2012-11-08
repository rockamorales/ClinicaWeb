/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.farmacia;

import sv.com.cormaria.clinica.web.managebeans.farmacia.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.farmacia.TblDetalleDespacho;

/**
 *
 * @author Mackk
 */
@ManagedBean
@ViewScoped
public class TblDetalleDespachoForm {
private TblDetalleDespacho tblDetalleIngresoProducto = new TblDetalleDespacho();

    public TblDetalleDespacho getTblDetalleDespacho() {
        return tblDetalleIngresoProducto;
    }

    public void setTblDetalleDespacho(TblDetalleDespacho tblDetalleIngresoProducto) {
        this.tblDetalleIngresoProducto = tblDetalleIngresoProducto;
    }


    /** Creates a new instance of TblDetalleDespachoForm */
    public TblDetalleDespachoForm() {
    }
}
