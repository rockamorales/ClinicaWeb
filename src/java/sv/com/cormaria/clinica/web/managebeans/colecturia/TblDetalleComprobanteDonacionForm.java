/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.colecturia;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.colecturia.TblDetalleComprobanteDonacion;

/**
 *
 * @author Mackk
 */
@ManagedBean
@ViewScoped
public class TblDetalleComprobanteDonacionForm {
private TblDetalleComprobanteDonacion tblDetalleComprobanteDonacion = new TblDetalleComprobanteDonacion();

    public TblDetalleComprobanteDonacion getTblDetalleComprobanteDonacion() {
        return tblDetalleComprobanteDonacion;
    }

    public void setTblDetalleComprobanteDonacion(TblDetalleComprobanteDonacion tblDetalleComprobanteDonacion) {
        this.tblDetalleComprobanteDonacion = tblDetalleComprobanteDonacion;
    }


    /** Creates a new instance of TblDetalleComprobanteDonacionForm */
    public TblDetalleComprobanteDonacionForm() {
    }
}
