package sv.com.cormaria.clinica.web.managebeans.consultasmedicas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblConsultasFacadeLocal;

/**
 *
 * @author GERARDO
 */
@ManagedBean
@RequestScoped
public class FrmMantSigVitales extends PageBase{

    /** Creates a new instance of FrmMantSigVitales */
    public FrmMantSigVitales() {
    }
    private TblConsultas tblConsultas = new TblConsultas();
    
    @EJB
    private TblConsultasFacadeLocal facade;
    @ManagedProperty(value ="#{param.numConsultas}")
    private Integer numConsultas;

    public Integer getNumConsultas() {
        return numConsultas;
    }

    public void setNumConsultas(Integer numConsultas) {
        if(numConsultas != null){
            try{
            tblConsultas = facade.find(numConsultas);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.numConsultas = numConsultas;
    }
    
    
    
    public TblConsultas getTblConsultas() {
        return tblConsultas;
    }

    public void setTblConsultas(TblConsultas tblConsultas) {
        this.tblConsultas = tblConsultas;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(tblConsultas.getNumConsulta() != null){
                facade.edit(tblConsultas);
            }else{
                facade.create(tblConsultas);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    
}
