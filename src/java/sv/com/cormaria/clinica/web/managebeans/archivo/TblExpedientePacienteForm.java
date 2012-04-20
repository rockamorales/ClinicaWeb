/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.archivo.TblTarjetaControlCitas;

/**
 *
 * @author Claudia
 */
@ManagedBean
@ViewScoped
public class TblExpedientePacienteForm implements Serializable {
    private TblExpedientePacientes tblExpedientePacientes = new TblExpedientePacientes();
    private TblTarjetaControlCitas tblTarjetaControlCitas = new TblTarjetaControlCitas();

    public TblTarjetaControlCitas getTblTarjetaControlCitas() {
        return tblTarjetaControlCitas;
    }

    public void setTblTarjetaControlCitas(TblTarjetaControlCitas tblTarjetaControlCitas) {
        this.tblTarjetaControlCitas = tblTarjetaControlCitas;
    }
    

    public TblExpedientePacientes getTblExpedientePacientes() {
        return tblExpedientePacientes;
    }

    public void setTblExpedientePacientes(TblExpedientePacientes tblExpedientePacientes) {
        System.out.println("Estableciendo la instancia diferente de expediente pacientes...");
        this.tblExpedientePacientes = tblExpedientePacientes;
    }
    
    

    /** Creates a new instance of TblExpedientePacienteForm */
    public TblExpedientePacienteForm() {
        System.out.println("Creando una instancia de TblExpedientepacienteform: ");
    }
}
