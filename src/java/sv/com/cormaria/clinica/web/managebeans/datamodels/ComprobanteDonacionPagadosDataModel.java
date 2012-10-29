/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.cormaria.clinica.web.managebeans.datamodels;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.clinica.web.managebeans.dataproviders.ClinicaDataProvider;
import sv.com.cormaria.servicios.entidades.colecturia.TblComprobanteDonacion;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class ComprobanteDonacionPagadosDataModel extends ClinicaDataModel<TblComprobanteDonacion, Integer> {

    @ManagedProperty(value="#{comprobanteDonacionPagadosDataProvider}")
    ClinicaDataProvider<TblComprobanteDonacion, Integer> dataProvider;
    
    @Override
    public ClinicaDataProvider<TblComprobanteDonacion, Integer> getDataProvider() {
        return dataProvider;
    }

    @Override
    public void setDataProvider(ClinicaDataProvider<TblComprobanteDonacion, Integer> dataProvider) {
        this.dataProvider = dataProvider;
    }
}