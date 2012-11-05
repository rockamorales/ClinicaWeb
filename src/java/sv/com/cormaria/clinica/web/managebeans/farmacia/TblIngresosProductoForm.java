/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.farmacia;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.farmacia.TblIngresosProducto;

/**
 *
 * @author Schumy
 */
@ManagedBean
@ViewScoped
public class TblIngresosProductoForm implements Serializable{
    private TblIngresosProducto tblIngresosProducto = new TblIngresosProducto();

    public TblIngresosProducto getTblIngresosProducto() {
        return tblIngresosProducto;
    }

    public void setTblIngresosProducto(TblIngresosProducto tblIngresosProducto) {
        this.tblIngresosProducto = tblIngresosProducto;
    }
   
    
    /** Creates a new instance of TblIngresosProductoForm */
    public TblIngresosProductoForm() {
    }
}
