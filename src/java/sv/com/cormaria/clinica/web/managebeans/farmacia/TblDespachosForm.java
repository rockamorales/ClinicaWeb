/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.farmacia;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.farmacia.TblDespachos;

/**
 *
 * @author Schumy
 */
@ManagedBean
@ViewScoped
public class TblDespachosForm implements Serializable{
    private TblDespachos tblDespachos = new TblDespachos();

    public TblDespachos getTblDespachos() {
        return tblDespachos;
    }

    public void setTblDespachos(TblDespachos tblDespachos) {
        this.tblDespachos = tblDespachos;
    }
   
    
    /** Creates a new instance of TblDespachosForm */
    public TblDespachosForm() {
    }
}

