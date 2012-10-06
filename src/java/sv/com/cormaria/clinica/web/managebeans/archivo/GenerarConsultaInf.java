package sv.com.cormaria.clinica.web.managebeans.archivo;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Administrador
 */
@ManagedBean
@ViewScoped
public class GenerarConsultaInf implements Serializable{
    private Integer codEspecialidad;
    private Integer codTipConsulta;
    private Integer numMedico;
    private String observaciones;
    /** Creates a new instance of GenerarConsultaInf */
    public GenerarConsultaInf() {
    }

    public Integer getCodEspecialidad() {
        return codEspecialidad;
    }

    public void setCodEspecialidad(Integer codEspecialidad) {
        this.codEspecialidad = codEspecialidad;
    }

    public Integer getCodTipConsulta() {
        return codTipConsulta;
    }

    public void setCodTipConsulta(Integer codTipConsulta) {
        this.codTipConsulta = codTipConsulta;
    }

    public Integer getNumMedico() {
        return numMedico;
    }

    public void setNumMedico(Integer numMedico) {
        this.numMedico = numMedico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
