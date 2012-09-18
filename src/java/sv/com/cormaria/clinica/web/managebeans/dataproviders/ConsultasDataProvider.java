/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.dataproviders;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.criteria.SearchCriteria;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblConsultasFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class ConsultasDataProvider implements ClinicaDataProvider<TblConsultas, Integer> {
    @EJB
    private TblConsultasFacadeLocal facade;

    @ManagedProperty(value="#{consultasSearchCriteria}")
    private SearchCriteria criteria;

    @Override
    public Integer getRowKey(TblConsultas o) {
            return o.getNumExpediente();
    }

    @Override
    public List<TblConsultas> getData(int firtRows, int numberOfRows) {
        if (criteria!=null){
              return facade.find(criteria, firtRows, numberOfRows);
        }
        return new ArrayList<TblConsultas>();
    }

    @Override
    public Integer getPk(TblConsultas item) {
            return item.getNumExpediente();
    }

    @Override
    public int getRowCount() {
        if (criteria!=null){
           return facade.count(criteria);
        }
        return 0;
    }

    @Override
    public TblConsultas getItemByPk(Integer pk) throws ClinicaModelexception {
      return facade.find(pk);
    }

    @Override
    public boolean exist(Integer pk) throws ClinicaModelexception {
            return facade.find(pk)!=null;
    }

    public SearchCriteria getCriteria() {
            return criteria;
    }

    public void setCriteria(SearchCriteria criteria) {
            this.criteria = criteria;
    }
}
