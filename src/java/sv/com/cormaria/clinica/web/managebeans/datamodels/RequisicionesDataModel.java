/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.cormaria.clinica.web.managebeans.datamodels;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.clinica.web.managebeans.dataproviders.ClinicaDataProvider;
import sv.com.cormaria.servicios.entidades.administracion.TblRequisiciones;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class RequisicionesDataModel extends ClinicaDataModel<TblRequisiciones, Integer> {

    @ManagedProperty(value="#{requisicionesDataProvider}")
    ClinicaDataProvider<TblRequisiciones, Integer> dataProvider;
    
    public ClinicaDataProvider<TblRequisiciones, Integer> getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(ClinicaDataProvider<TblRequisiciones, Integer> dataProvider) {
        this.dataProvider = dataProvider;
    }
}