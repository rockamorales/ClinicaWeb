/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.cormaria.clinica.web.managebeans.datamodels;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.clinica.web.managebeans.dataproviders.ClinicaDataProvider;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class ConsultasDataModel extends ClinicaDataModel<TblConsultas, Integer> {

    @ManagedProperty(value="#{consultasDataProvider}")
    ClinicaDataProvider<TblConsultas, Integer> dataProvider;
    
    @Override
    public ClinicaDataProvider<TblConsultas, Integer> getDataProvider() {
        return dataProvider;
    }

    @Override
    public void setDataProvider(ClinicaDataProvider<TblConsultas, Integer> dataProvider) {
        this.dataProvider = dataProvider;
    }
}