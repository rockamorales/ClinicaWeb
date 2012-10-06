/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.cormaria.clinica.web.managebeans.datamodels;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.clinica.web.managebeans.dataproviders.ClinicaDataProvider;
import sv.com.cormaria.servicios.entidades.archivo.TblServiciosEnfermeria;
import sv.com.cormaria.servicios.exceptions.ClinicaModelValidationException;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class ServiciosEnfermeriaDataModel extends ClinicaDataModel<TblServiciosEnfermeria, Integer> {

    @ManagedProperty(value="#{serviciosEnfermeriaDataProvider}")
    ClinicaDataProvider<TblServiciosEnfermeria, Integer> dataProvider;
    
    public ClinicaDataProvider<TblServiciosEnfermeria, Integer> getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(ClinicaDataProvider<TblServiciosEnfermeria, Integer> dataProvider) {
        this.dataProvider = dataProvider;
    }
}