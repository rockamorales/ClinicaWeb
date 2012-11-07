/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo.reportes;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.enums.EstadoProgramacionCitas;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean
@RequestScoped
public class RptCitas {
    
    @EJB
    private TblMedicoFacadeLocal medicoFacade;
    
    private List<TblMedico> medicosList = new ArrayList<TblMedico>();
    /**
     * Creates a new instance of RptCitas
     */
    public RptCitas() {
    }

    public TblMedicoFacadeLocal getMedicoFacade() {
        return medicoFacade;
    }

    public void setMedicoFacade(TblMedicoFacadeLocal medicoFacade) {
        this.medicoFacade = medicoFacade;
    }

    public List<TblMedico> getMedicosList() {
        if (medicosList.isEmpty()){
            try{
                medicosList = medicoFacade.findActive();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return medicosList;
    }

    public void setMedicosList(List<TblMedico> medicosList) {
        this.medicosList = medicosList;
    }
}
