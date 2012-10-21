/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.cormaria.clinica.web.managebeans.datamodels;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.clinica.web.managebeans.dataproviders.ClinicaDataProvider;
import sv.com.cormaria.servicios.entidades.farmacia.TblAlquilerEquipo;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class AlquileresDataModel extends ClinicaDataModel<TblAlquilerEquipo, Integer> {

    @ManagedProperty(value="#{alquileresDataProvider}")
    ClinicaDataProvider<TblAlquilerEquipo, Integer> dataProvider;
    
    @Override
    public ClinicaDataProvider<TblAlquilerEquipo, Integer> getDataProvider() {
        return dataProvider;
    }

    @Override
    public void setDataProvider(ClinicaDataProvider<TblAlquilerEquipo, Integer> dataProvider) {
        this.dataProvider = dataProvider;
    }
}