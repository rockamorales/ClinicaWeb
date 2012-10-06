/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.datamodels;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.clinica.web.managebeans.dataproviders.ClinicaDataProvider;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class ExpedienteDataModel extends ClinicaDataModel<TblExpedientePacientes, Integer> {

    @ManagedProperty(value="#{expedienteDataProvider}")
    ClinicaDataProvider<TblExpedientePacientes, Integer> dataProvider;
    
    @Override
    public ClinicaDataProvider<TblExpedientePacientes, Integer> getDataProvider() {
        return dataProvider;
    }

    @Override
    public void setDataProvider(ClinicaDataProvider<TblExpedientePacientes, Integer> dataProvider) {
        this.dataProvider = dataProvider;
    }
}
