/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.archivo.TblServiciosEnfermeria;
import sv.com.cormaria.servicios.facades.archivo.TblServiciosEnfermeriaFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmConsultaViewTblServiciosEnfermeria extends PageBase{

    @EJB
    private TblServiciosEnfermeriaFacadeLocal serviciosEnfermeriaFacade;
    
    private TblExpedientePacientes tblExpediente = new TblExpedientePacientes();
    
    private TblServiciosEnfermeria tblServiciosEnfermeria = new TblServiciosEnfermeria();
    
    private Integer numSerEnfermeria;
    
    /**
     * Creates a new instance of FrmConsultaViewTblServiciosEnfermeria
     */
    public FrmConsultaViewTblServiciosEnfermeria() {
    }

    public Integer getNumSerEnfermeria() {
        return numSerEnfermeria;
    }

    public void setNumSerEnfermeria(Integer numSerEnfermeria) {
        this.numSerEnfermeria = numSerEnfermeria;
    }
    
    public TblExpedientePacientes getTblExpediente() {
        return tblExpediente;
    }

    public void setTblExpediente(TblExpedientePacientes tblExpediente) {
        this.tblExpediente = tblExpediente;
    }

    public TblServiciosEnfermeria getTblServiciosEnfermeria() {
        return tblServiciosEnfermeria;
    }

    public void setTblServiciosEnfermeria(TblServiciosEnfermeria tblServiciosEnfermeria) {
        this.tblServiciosEnfermeria = tblServiciosEnfermeria;
    }    
    
    public void init(){
        if (this.getTblServiciosEnfermeria()==null || this.getTblServiciosEnfermeria().getNumSerEnfermeria()==null || this.getTblServiciosEnfermeria().getNumSerEnfermeria()<=0){
            if (numSerEnfermeria != null && numSerEnfermeria > 0){
                    try{
                        this.tblServiciosEnfermeria = this.serviciosEnfermeriaFacade.find(numSerEnfermeria);
                        this.tblExpediente = this.tblServiciosEnfermeria.getExpediente();
                    }catch(Exception ex){
                        ex.printStackTrace();
                        this.addError(ex.getMessage(), ex.getMessage());
                    }
            }
        }		
    }

}
