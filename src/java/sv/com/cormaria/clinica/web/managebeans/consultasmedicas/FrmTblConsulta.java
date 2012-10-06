/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.consultasmedicas;

import java.util.List;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblConsultasFacadeLocal;

/**
 *
 * @author Administrador
 */
@ManagedBean
@RequestScoped
public class FrmTblConsulta {
    private TblConsultas consulta = new TblConsultas();

    private List<TblConsultas> consultasList = new ArrayList<TblConsultas>();
    
    @EJB
    TblConsultasFacadeLocal consultasFacade;

    public FrmTblConsulta() {
    }
    
    public TblConsultas getConsulta() {
        return consulta;
    }

    public void setConsulta(TblConsultas consulta) {
        this.consulta = consulta;
    }

    public TblConsultasFacadeLocal getConsultasFacade() {
        return consultasFacade;
    }

    public void setConsultasFacade(TblConsultasFacadeLocal consultasFacade) {
        this.consultasFacade = consultasFacade;
    }

    public List<TblConsultas> getConsultasList() {
        if (this.consultasList.isEmpty()){
            try{
                consultasList = consultasFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return consultasList;
    }

    public void setConsultasList(List<TblConsultas> consultasList) {
        this.consultasList = consultasList;
    }
}
