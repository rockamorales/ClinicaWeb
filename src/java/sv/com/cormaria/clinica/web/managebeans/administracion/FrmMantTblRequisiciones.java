/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.administracion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblRequisiciones;
import sv.com.cormaria.servicios.facades.administracion.TblRequisicionesFacade;
import sv.com.cormaria.servicios.facades.administracion.TblRequisicionesFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantTblRequisiciones")
@RequestScoped
public class FrmMantTblRequisiciones extends PageBase{
    private TblRequisiciones tblRequisiciones = new TblRequisiciones();
    
    @EJB
    private TblRequisicionesFacadeLocal facade;
    @ManagedProperty(value ="#{param.numRequisicion}")
    private Integer numRequisicion;

    public Integer getNumRequisicion() {
        return numRequisicion;
    }

    public void setNumRequisicion(Integer numRequisicion) {
        if(numRequisicion != null){
            try{
            tblRequisiciones = facade.find(numRequisicion);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.numRequisicion = numRequisicion;
    }
    
    
    
    public TblRequisiciones getTblRequisiciones() {
        return tblRequisiciones;
    }

    public void setTblRequisiciones(TblRequisiciones tblRequisiciones) {
        this.tblRequisiciones = tblRequisiciones;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(tblRequisiciones.getNumRequisicion() != null){
                facade.edit(tblRequisiciones);
            }else{
                facade.create(tblRequisiciones);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblRequisiciones = new TblRequisiciones();
    }
    
    
}
