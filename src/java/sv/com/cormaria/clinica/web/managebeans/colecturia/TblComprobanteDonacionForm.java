/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.colecturia;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.colecturia.TblComprobanteDonacion;

/**
 *
 * @author Mackk
 */
@ManagedBean
@ViewScoped
public class TblComprobanteDonacionForm implements Serializable{
    private TblComprobanteDonacion tblComprobanteDonacion = new TblComprobanteDonacion();

    public TblComprobanteDonacion getTblComprobanteDonacion() {
        return tblComprobanteDonacion;
    }

    public void setTblComprobanteDonacion(TblComprobanteDonacion tblComprobanteDonacion) {
        this.tblComprobanteDonacion = tblComprobanteDonacion;
    }
   
    
    /** Creates a new instance of TblComprobanteDonacionForm */
    public TblComprobanteDonacionForm() {
    }
}
