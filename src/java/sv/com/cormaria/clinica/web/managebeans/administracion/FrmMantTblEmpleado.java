/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.administracion;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblEmpleado;
import sv.com.cormaria.servicios.facades.administracion.TblEmpleadoFacade;
import sv.com.cormaria.servicios.facades.administracion.TblEmpleadoFacadeLocal;

/**
 *
 * @author Claudia
 */
@ManagedBean (name = "frmMantTblEmpleado")
@RequestScoped
public class FrmMantTblEmpleado extends PageBase{
    private TblEmpleado tblEmpleado = new TblEmpleado();

    @EJB
    private TblEmpleadoFacadeLocal facade;
    @ManagedProperty(value ="#{param.numEmpleado}")
    private Integer numEmpleado;
    
    public Integer getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(Integer numEmpleado) {
        if(numEmpleado != null){
            try{
            tblEmpleado = facade.find(numEmpleado);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.numEmpleado = numEmpleado;
    }
    
    
    
    public TblEmpleado getTblEmpleado() {
        return tblEmpleado;
    }

    public void setTblEmpleado(TblEmpleado tblEmpleado) {
        this.tblEmpleado = tblEmpleado;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(tblEmpleado.getNumEmpleado() != null){
                facade.edit(tblEmpleado);
            }else{
                facade.create(tblEmpleado);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblEmpleado = new TblEmpleado();
    }
    
    
}
